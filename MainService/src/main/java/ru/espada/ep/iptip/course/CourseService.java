package ru.espada.ep.iptip.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.espada.ep.iptip.course.learning_resource_category.CourseLearningResourceCategoryEntity;
import ru.espada.ep.iptip.course.learning_resource_category.model.CourseLearningResourceCategoryEntityDto;
import ru.espada.ep.iptip.course.learning_resource_category.CourseLearningResourceCategoryRepository;
import ru.espada.ep.iptip.course.model.CourseEntityDto;
import ru.espada.ep.iptip.course.model.CreateCourseModel;
import ru.espada.ep.iptip.course.model.CreateCourseLearningResourceCategoryModel;
import ru.espada.ep.iptip.course.model.StudyGroupCourseModel;
import ru.espada.ep.iptip.course.test.model.CreateTestModel;
import ru.espada.ep.iptip.course.user_course.UserCourseEntity;
import ru.espada.ep.iptip.course.user_course.UserCourseRepository;
import ru.espada.ep.iptip.exceptions.custom.ForbiddenException;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;
import ru.espada.ep.iptip.study_groups.StudyGroupRepository;
import ru.espada.ep.iptip.study_groups.StudyGroupService;
import ru.espada.ep.iptip.university.institute.major.faculty.FacultyEntity;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.UserRepository;
import ru.espada.ep.iptip.user.UserService;
import ru.espada.ep.iptip.user.permission.UserPermissionService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private UserCourseRepository userCourseRepository;
    private StudyGroupRepository studyGroupRepository;
    private UserRepository userRepository;
    private UserService userService;
    private StudyGroupService studyGroupService;
    private CourseLearningResourceCategoryRepository courseLearningResourceCategoryRepository;
    private UserPermissionService userPermissionService;

    @Transactional
    public Long createCourse(Principal principal, CreateCourseModel createCourseModel) {
        CourseEntity courseEntity = CourseEntity.builder()
                .name(createCourseModel.getName())
                .description(createCourseModel.getDescription())
                .faculties(Set.of(FacultyEntity.builder().id(createCourseModel.getFacultyId()).build()))
                .build();
        courseRepository.save(courseEntity);

        userPermissionService.addPermission(principal.getName(), "course.amdin." + courseEntity.getId(), -1L, -1L);

        return courseEntity.getId();
    }

    public void deleteCourse(Principal principal, Long id) {
        courseRepository.deleteById(id);
    }

    public CourseEntity getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public CourseEntityDto getCourseDto(Long id) {
        CourseEntity courseEntity = getCourse(id);
        return CourseEntityDto.builder()
                .id(courseEntity.getId())
                .createdAt(courseEntity.getCreatedAt())
                .name(courseEntity.getName())
                .description(courseEntity.getDescription())
                .build();
    }

    public List<CourseLearningResourceCategoryEntityDto> getCourseLearningResourceCategories(Long id) {
        return courseLearningResourceCategoryRepository.findCourseLearningResourceCategoryEntitiesByCourse(CourseEntity.builder().id(id).build()).stream().map(
                courseLearningResourceCategoryEntity -> CourseLearningResourceCategoryEntityDto.builder()
                        .id(courseLearningResourceCategoryEntity.getId())
                        .name(courseLearningResourceCategoryEntity.getName())
                        .build()
        ).toList();
    }

    @Transactional
    public UserCourseEntity attachUserToCourse(Long courseId, Long userId, int semester) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        return userCourseRepository.save(UserCourseEntity.builder()
                .courseId(courseEntity.getId())
                .userId(userEntity.getId())
                .semester(semester)
                .build());
    }

    @Transactional
    public void detachUserFromCourse(Long courseId, Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        UserCourseEntity userCourseEntity = userCourseRepository.findUserCourseEntityByUserIdAndCourseId(userEntity.getId(), courseEntity.getId());
        userCourseRepository.delete(userCourseEntity);
    }

    @Transactional
    public void attachStudyGroupToCourse(Principal principal, StudyGroupCourseModel attachStudyGroupToCourseModel) {
        if (!studyGroupService.hasPermission(principal.getName(), attachStudyGroupToCourseModel.getCourseId())) throw new ForbiddenException("Permission denied");

        CourseEntity courseEntity = courseRepository.findById(attachStudyGroupToCourseModel.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
        StudyGroupEntity studyGroupEntity = studyGroupRepository.findById(attachStudyGroupToCourseModel.getStudyGroupId()).orElseThrow(() -> new RuntimeException("StudyGroup not found"));
        List<UserEntity> users = studyGroupEntity.getUsers().stream().toList();

        userCourseRepository.saveAll(users.stream().map(user -> UserCourseEntity.builder()
                .courseId(courseEntity.getId())
                .userId(user.getId())
                .build()).toList());

    }

    @Transactional
    public void detachStudyGroupFromCourse(Principal principal, StudyGroupCourseModel detachUserFromCourseModel) {
        if (!studyGroupService.hasPermission(principal.getName(), detachUserFromCourseModel.getCourseId())) throw new ForbiddenException("Permission denied");

        CourseEntity courseEntity = courseRepository.findById(detachUserFromCourseModel.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
        StudyGroupEntity studyGroupEntity = studyGroupRepository.findById(detachUserFromCourseModel.getStudyGroupId()).orElseThrow(() -> new RuntimeException("StudyGroup not found"));
        List<UserEntity> users = studyGroupEntity.getUsers().stream().toList();

        userCourseRepository.deleteAll(users.stream().map(user -> UserCourseEntity.builder()
                .courseId(courseEntity.getId())
                .userId(user.getId())
                .build()).toList());

    }

    public List<Long> getUserCourses(Long userId) {
        return this.userCourseRepository.findUserCourseEntitiesByUserId(userId).stream().map(UserCourseEntity::getCourseId).toList();
    }

    public Long createCourseLearningResourceCategory(CreateCourseLearningResourceCategoryModel createCourseLearningResourceCategoryModel) {
        CourseLearningResourceCategoryEntity courseLearningResourceCategoryEntity = CourseLearningResourceCategoryEntity.builder()
                .name(createCourseLearningResourceCategoryModel.getName())
                .course(CourseEntity.builder().id(createCourseLearningResourceCategoryModel.getCourseId()).build())
                .build();

        return courseLearningResourceCategoryRepository.save(courseLearningResourceCategoryEntity).getId();
    }

    public void deleteCourseLearningResourceCategory(Long id) {
        courseLearningResourceCategoryRepository.deleteById(id);
    }

    public Long createTest(CreateTestModel createTestModel) {
        return 0L;
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

    @Autowired
    public void setCourseLearningResourceCategoryRepository(CourseLearningResourceCategoryRepository courseLearningResourceCategoryRepository) {
        this.courseLearningResourceCategoryRepository = courseLearningResourceCategoryRepository;
    }

    @Autowired
    public void setStudyGroupService(StudyGroupService studyGroupService) {
        this.studyGroupService = studyGroupService;
    }

    @Autowired
    public void setUserPermissionService(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }
}
