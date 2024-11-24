package ru.espada.ep.iptip.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.espada.ep.iptip.course.model.CourseResponseModel;
import ru.espada.ep.iptip.course.model.CreateCourseModel;
import ru.espada.ep.iptip.course.test.TestEntity;
import ru.espada.ep.iptip.course.user_course.UserCourseEntity;
import ru.espada.ep.iptip.course.user_course.UserCourseRepository;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;
import ru.espada.ep.iptip.study_groups.StudyGroupRepository;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.UserRepository;
import ru.espada.ep.iptip.user.UserService;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private UserCourseRepository userCourseRepository;
    private StudyGroupRepository studyGroupRepository;
    private UserRepository userRepository;
    private UserService userService;


    public void createCourse(Principal principal, CreateCourseModel createCourseModel) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        CourseEntity courseEntity = CourseEntity.builder()
                .name(createCourseModel.getName())
                .description(createCourseModel.getDescription())
                .build();
        courseRepository.save(courseEntity);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public CourseEntity getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Transactional
    public UserCourseEntity attachUserToCourse(Long courseId, Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        return userCourseRepository.save(UserCourseEntity.builder()
                .courseId(courseEntity.getId())
                .userId(userEntity.getId())
                .build());
    }

    @Transactional
    public Boolean detachUserFromCourse(Long courseId, Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        UserCourseEntity userCourseEntity = userCourseRepository.findUserCourseEntityByUserIdAndCourseId(userEntity.getId(), courseEntity.getId());
        userCourseRepository.delete(userCourseEntity);
        return true;
    }

    @Transactional
    public Boolean attachStudyGroupToCourse(Long courseId, Long studyGroupId) {
        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        StudyGroupEntity studyGroupEntity = studyGroupRepository.findById(studyGroupId).orElseThrow(() -> new RuntimeException("StudyGroup not found"));
        List<UserEntity> users = studyGroupEntity.getUsers().stream().toList();

        userCourseRepository.saveAll(users.stream().map(user -> UserCourseEntity.builder()
                .courseId(courseEntity.getId())
                .userId(user.getId())
                .build()).toList());

        return true;
    }

    @Transactional
    public Boolean detachStudyGroupFromCourse(Long courseId, Long studyGroupId) {
        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        StudyGroupEntity studyGroupEntity = studyGroupRepository.findById(studyGroupId).orElseThrow(() -> new RuntimeException("StudyGroup not found"));
        List<UserEntity> users = studyGroupEntity.getUsers().stream().toList();

        userCourseRepository.deleteAll(users.stream().map(user -> UserCourseEntity.builder()
                .courseId(courseEntity.getId())
                .userId(user.getId())
                .build()).toList());

        return true;
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
    public void setStudyGroupRepository(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<CourseEntity> getUserCourses(Long userId) {
        return this.userCourseRepository.findUserCourseEntitiesByUserId(userId)
                .stream()
                .map(userCourseEntity -> courseRepository.findById(userCourseEntity.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found")))
                .sorted(Comparator.comparing(CourseEntity::getName))
                .toList();
    }
}
