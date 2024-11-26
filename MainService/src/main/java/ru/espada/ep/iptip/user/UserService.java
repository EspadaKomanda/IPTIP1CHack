package ru.espada.ep.iptip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.espada.ep.iptip.course.*;
import ru.espada.ep.iptip.course.model.CourseEntityDto;
import ru.espada.ep.iptip.course.user_course.UserCourseEntity;
import ru.espada.ep.iptip.course.user_course.UserCourseRepository;
import ru.espada.ep.iptip.s3.S3Service;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;
import ru.espada.ep.iptip.study_groups.StudyGroupRepository;
import ru.espada.ep.iptip.study_groups.user.UserStudyGroupEntity;
import ru.espada.ep.iptip.study_groups.user.UserStudyGroupEntityRepository;
import ru.espada.ep.iptip.university.UniversityEntity;
import ru.espada.ep.iptip.university.institute.InstituteEntity;
import ru.espada.ep.iptip.university.institute.major.MajorEntity;
import ru.espada.ep.iptip.university.institute.major.faculty.FacultyEntity;
import ru.espada.ep.iptip.university.institute.major.faculty.FacultyRepository;
import ru.espada.ep.iptip.user.models.response.InstituteInfoResponse;
import ru.espada.ep.iptip.user.models.response.UserDto;
import ru.espada.ep.iptip.user.models.response.UserInstituteDto;
import ru.espada.ep.iptip.user.profile.ProfileEntity;
import ru.espada.ep.iptip.user.profile.ProfileRepository;
import ru.espada.ep.iptip.user.models.request.AddRoleRequest;
import ru.espada.ep.iptip.user.models.request.CreateProfileRequest;
import ru.espada.ep.iptip.user.permission.UserPermissionService;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private CourseRepository courseRepository;
    private UserCourseRepository userCourseRepository;
    private FacultyRepository facultyRepository;
    private PasswordEncoder bCryptPasswordEncoder;
    private UserPermissionService userPermissionService;
    @Value("${users.page-size}")
    private int pageSize;
    private S3Service s3Service;
    private UserStudyGroupEntityRepository userStudyGroupRepository;
    private StudyGroupRepository studyGroupRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    @Cacheable(value = "users", key = "'users'", unless = "#result == null")
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "users", key = "#page - 'users'", unless = "#result == null")
    @Transactional
    public List<UserDto> allUsers(int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return userRepository.findAll(pageable).stream().
                map(this::toUserDto).toList();
    }

    @CacheEvict(value = "users", key = "'users'")
    public void saveUser(UserEntity user) {
        UserEntity userFromDB = userRepository.findByUsername(user.getUsername()).orElse(null);

        if (userFromDB != null) {
            throw new IllegalArgumentException("exception.user.user_exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "'users'")
    public void deleteUser(String deleteUserId) {
        UserEntity user = userRepository.findByUsername(deleteUserId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDto getUserDto(Long id) {
        return toUserDto(getUser(id));
    }

    public UserDto getUserDto(String username) {
        return toUserDto(getUser(username));
    }

    @Transactional
    public void createProfile(Principal principal, CreateProfileRequest createProfileRequest) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!Objects.equals(user.getId(), createProfileRequest.getUserId())) {
            throw new IllegalArgumentException("exception.user.only_owner");
        }
        if (user.getProfile() != null) {
            throw new IllegalArgumentException("exception.user.customer_exists");
        }

        ProfileEntity profile = new ProfileEntity();
        profile.setName(createProfileRequest.getName());
        profile.setSurname(createProfileRequest.getSurname());
        profile.setPhone(createProfileRequest.getPhone());
        profile.setEmail(createProfileRequest.getEmail());
        profile.setSemester(createProfileRequest.getSemester());
        profile.setStudentIdCard(createProfileRequest.getStudentIdCard());
        if (createProfileRequest.getPatronymic() != null) {
            profile.setPatronymic(createProfileRequest.getPatronymic());
        }
        if (createProfileRequest.getBirthDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                profile.setBirthDate(formatter.parse(createProfileRequest.getBirthDate()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        profileRepository.save(profile);
        user.setProfile(profile);
        userRepository.save(user);
    }

    @Transactional
    public String uploadAvatar(String name, byte[] avatar) {
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String profileUrl = s3Service.uploadPng(avatar, "user-" + user.getId(), "user-avatar")
                .thenCompose(fileName -> {
                    if (fileName == null) {
                        throw new IllegalArgumentException("exception.user.avatar_upload_failed");
                    } else {
                        return s3Service.getFileUrl("user-avatar", fileName);
                    }
                }).thenApply(url -> url).join();
        user.getProfile().setIcon(profileUrl);
        userRepository.save(user);

        return profileUrl;
    }

    public String getAvatarUrl(String name) {
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return s3Service.getFileUrl("user-avatar", "user-" + user.getId()).join();
    }

    @Transactional
    public InstituteInfoResponse getInstituteInfo(String username) {

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ProfileEntity profile = user.getProfile();
        int semester = profile.getSemester();
        int course = semester / 2;

        StudyGroupEntity studyGroup = user.getStudyGroups().stream().findFirst().orElse(null);

        // FIXME: this may be null
        FacultyEntity faculty = studyGroup == null ? null : studyGroup.getFaculty();
        MajorEntity major = faculty == null ? null : faculty.getMajor();
        InstituteEntity insitute = major == null ? null :  major.getInstitute();
        UniversityEntity university = insitute == null ? null : insitute.getUniversity();

        return InstituteInfoResponse.builder()
                .major(major == null ? null : major.getName())
                .majorCode(major == null ? null : major.getMajorCode())
                .faculty(faculty == null ? null : faculty.getName())
                .institute(insitute == null ? null : insitute.getName())
                .university(university == null ? null : university.getName())
                .studyGroup(studyGroup == null ? null : studyGroup.getName())
                .semester(semester)
                .course(course)
                .build();
    }

    public List<CourseEntityDto> getUserCourses(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<UserCourseEntity> userCourses = userCourseRepository.findUserCourseEntitiesByUserIdAndSemester(user.getId(), user.getProfile().getSemester());
        List<CourseEntity> courses = courseRepository.findAllById(userCourses.stream().map(UserCourseEntity::getCourseId).toList());

        return courses.stream().map(course ->
                CourseEntityDto.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .description(course.getDescription())
                        .createdAt(course.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public void addPermission(String username, AddRoleRequest addRoleRequest) {
        String permission = addRoleRequest.getPermission();
        for (String key : addRoleRequest.getCredentials().keySet()) {
            permission = permission.replace("{" + key + "}", addRoleRequest.getCredentials().get(key));
        }

        if (!userPermissionService.hasParentPermission(username, permission)) {
            throw new RuntimeException("exception.user.permission_not_found");
        }

        userPermissionService.addPermission(addRoleRequest.getUsername(), permission, addRoleRequest.getStartTime(), addRoleRequest.getEndTime());
    }

    public List<UserStudyGroupEntity> getUserStudyGroups(Long userId) {
        return userStudyGroupRepository.findUserStudyGroupEntitiesByUserId(userId);
    }

    public UserStudyGroupEntity getMainUserStudyGroup(Long userId) {
        return userStudyGroupRepository.findUserStudyGroupEntitiesByUserIdAndMain(userId, true);
    }

    private UserDto toUserDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setCreatedAt(user.getCreatedAt());

        if (user.getProfile() != null) {
            userDto.setProfileName(user.getProfile().getName());
            userDto.setProfileSurname(user.getProfile().getSurname());
            userDto.setProfilePatronymic(user.getProfile().getPatronymic());
            userDto.setProfilePhone(user.getProfile().getPhone());
            userDto.setProfileEmail(user.getProfile().getEmail());
            userDto.setProfileBirthDate(user.getProfile().getBirthDate());
            userDto.setProfileStudentIdCard(user.getProfile().getStudentIdCard());
            userDto.setProfileIcon(user.getProfile().getIcon());
            userDto.setProfileEmailConfirmed(user.getProfile().isEmailConfirmed());
            userDto.setProfilePhoneConfirmed(user.getProfile().isPhoneConfirmed());
            userDto.setProfileSemester(user.getProfile().getSemester());
        }

        UserStudyGroupEntity mainUserStudyGroup = getMainUserStudyGroup(user.getId());
        if (mainUserStudyGroup != null) {
            userDto.setStudyGroup(studyGroupRepository.findById(getMainUserStudyGroup(user.getId()).getStudyGroupId()).orElseThrow(() -> new RuntimeException("exception.study_group.not_found")).getName());
        }
        return userDto;
    }

    @Transactional
    public CourseFullDto getCourseFullDto(Principal principal, Long id) {
        CourseEntity course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new RuntimeException("exception.course.not_found");
        }
        UserEntity user = getUser(principal.getName());

        if (!userCourseRepository.existsByUserIdAndCourseId(user.getId(), course.getId())) {
            throw new RuntimeException("exception.user.not_in_course");
        }

        return CourseFullDto.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .createdAt(course.getCreatedAt())
                .teachers(course.getTeachers().stream().map(teacher -> TeacherEntityDto.builder()
                        .id(teacher.getId())
                        .name(teacher.getProfile().getName())
                        .surname(teacher.getProfile().getSurname())
                        .patronymic(teacher.getProfile().getPatronymic())
                        .icon(teacher.getProfile().getIcon())
                        .build()).collect(Collectors.toSet()))
                .tests(course.getTests().stream().map(test -> CourseTestEntityDto.builder()
                        .id(test.getId())
                        .name(test.getName())
                        .time(test.getTime())
                        .endTime(test.getEndTime())
                        .hideAnswerCorrectness(test.isHideAnswerCorrectness())
                        .hideAnswers(test.isHideAnswers())
                        .hideResultScore(test.isHideResultScore())
                        .build()
                ).collect(Collectors.toSet()))
                .build();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    @Lazy
    public void setBCryptPasswordEncoder(PasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setCustomerRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setUserCourseRepository(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }

    @Autowired
    public void setUserPermissionService(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }

    @Transactional
    public UserInstituteDto getUserInstituteDto(String name) {
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new RuntimeException("exception.user.not_found"));
        UserStudyGroupEntity userStudyGroup = userStudyGroupRepository.findUserStudyGroupEntitiesByUserId(user.getId()).stream().filter(UserStudyGroupEntity::isMain).findFirst().orElseThrow(() -> new RuntimeException("exception.user.not_found"));
        StudyGroupEntity studyGroup = studyGroupRepository.findById(userStudyGroup.getStudyGroupId()).orElseThrow(() -> new RuntimeException("exception.study_group.not_found"));

        return UserInstituteDto.builder()
                .universityName(studyGroup.getFaculty().getMajor().getInstitute().getUniversity().getName())
                .instituteName(studyGroup.getFaculty().getMajor().getInstitute().getName())
                .majorName(studyGroup.getFaculty().getMajor().getName())
                .majorCode(studyGroup.getFaculty().getMajor().getMajorCode())
                .facultyName(studyGroup.getFaculty().getName())
                .build();
    }

    @Autowired
    public void setUserStudyGroupRepository(UserStudyGroupEntityRepository userStudyGroupRepository) {
        this.userStudyGroupRepository = userStudyGroupRepository;
    }

    @Autowired
    public void setS3Service(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Autowired
    public void setStudyGroupRepository(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    public ScheduleDto getSchedule(Principal principal, Long date, int days) {

    }
}
