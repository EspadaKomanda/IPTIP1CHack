package ru.espada.ep.iptip.course;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.course.model.CreateCourseLearningResourceCategoryModel;
import ru.espada.ep.iptip.course.model.*;
import ru.espada.ep.iptip.course.test.model.CreateTestModel;

import java.security.Principal;
import java.util.List;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    //
    // Курс CRUD
    //

    @PostMapping("course")
    @PreAuthorize("hasPermission(#createCourseModel, 'university.{universityId}.institute.{instituteIdd}.major.{majorId}.faculty.{facultyId}.courses')")
    public ResponseEntity<?> createCourse(Principal principal, @Valid @RequestBody CreateCourseModel createCourseModel) {
        Long id = courseService.createCourse(principal, createCourseModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'course.amdin.{id}')")
    public ResponseEntity<?> deleteCourse(Principal principal, @PathVariable Long id) {
        courseService.deleteCourse(principal, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseEntityDto> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseDto(id));
    }

    //
    // Пользователи
    //

    @PostMapping("attachUser")
    @PreAuthorize("hasPermission(#attachUserToCourseModel, 'course.amdin.{courseId}')")
    public ResponseEntity<?> attachUserToCourse(@Valid @RequestBody UserCourseModel attachUserToCourseModel) {
        courseService.attachUserToCourse(
                attachUserToCourseModel.getCourseId(),
                attachUserToCourseModel.getUserId()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("detachUser")
    @PreAuthorize("hasPermission(#detachUserFromCourseModel, 'course.amdin.{courseId}')")
    public ResponseEntity<?> detachUserFromCourse(@Valid @RequestBody UserCourseModel detachUserFromCourseModel) {
        courseService.detachUserFromCourse(
                detachUserFromCourseModel.getCourseId(),
                detachUserFromCourseModel.getUserId()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("userCourses/{userId}")
    public ResponseEntity<List<Long>> getUserCourses(@PathVariable Long userId) {
        return ResponseEntity.ok(courseService.getUserCourses(userId));
    }

    //
    // Ученические группы
    //

    // защищено!
    @PostMapping("attachStudyGroup")
    public ResponseEntity<?> attachStudyGroupToCourse(Principal principal, @Valid @RequestBody StudyGroupCourseModel attachStudyGroupToCourseModel) {
        courseService.attachStudyGroupToCourse(principal, attachStudyGroupToCourseModel);
        return ResponseEntity.ok().build();
    }

    // защищено!
    @DeleteMapping("detachStudyGroup/")
    public ResponseEntity<?> detachStudyGroupFromCourse(Principal principal, @Valid @RequestBody StudyGroupCourseModel detachStudyGroupFromCourseModel) {
        courseService.detachStudyGroupFromCourse(principal, detachStudyGroupFromCourseModel);
        return ResponseEntity.ok().build();
    }

    //
    // Ресурсы курсов
    //

    @PostMapping("/resource/category")
    @PreAuthorize("hasPermission(#createCourseLearningResourceCategoryModel, 'course.amdin.{courseId}')")
    public ResponseEntity<Long> createCourseLearningResourceCategory(@Valid @RequestBody CreateCourseLearningResourceCategoryModel createCourseLearningResourceCategoryModel) {
        Long id = courseService.createCourseLearningResourceCategory(createCourseLearningResourceCategoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @DeleteMapping("/resource/category/{id}")
    @PreAuthorize("hasPermission(#id, 'course.amdin.{id}')")
    public ResponseEntity<Long> deleteCourseLearningResourceCategory(@PathVariable Long id) {
        courseService.deleteCourseLearningResourceCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //
    // Тесты
    //

    @PostMapping("/test")
    @PreAuthorize("hasPermission(#createTestModel, 'course.amdin.{courseId}')")
    public ResponseEntity<Long> createTest(@Valid @RequestBody CreateTestModel createTestModel) {
        Long id = courseService.createTest(createTestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
