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

import java.security.Principal;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

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

    @GetMapping("userCourses/{userId}")
    public ResponseEntity<?> getUserCourses(@PathVariable Long userId) {
        return ResponseEntity.ok(courseService.getUserCourses(userId));
    }

    @PostMapping("course")
    @PreAuthorize("hasPermission(#createCourseModel, 'university.{universityId}.institute.{instituteIdd}.major.{majorId}.faculty.{facultyId}.courses')")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CreateCourseModel createCourseModel) {
        Long id = courseService.createCourse(createCourseModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'course.amdin.{id}')")
    public ResponseEntity<?> deleteCourse(Principal principal, @PathVariable Long id) {
        courseService.deleteCourse(principal, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @PostMapping("/resource/category")
    @PreAuthorize("hasPermission(#createCourseLearningResourceCategoryModel, 'course.amdin.{courseId}')")
    public ResponseEntity<?> createCourseLearningResourceCategory(@Valid @RequestBody CreateCourseLearningResourceCategoryModel createCourseLearningResourceCategoryModel) {
        Long id = courseService.createCourseLearningResourceCategory(createCourseLearningResourceCategoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @DeleteMapping("/resource/category/{id}")
    @PreAuthorize("hasPermission(#id, 'course.amdin.{id}')")
    public ResponseEntity<?> deleteCourseLearningResourceCategory(@PathVariable Long id) {
        courseService.deleteCourseLearningResourceCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
