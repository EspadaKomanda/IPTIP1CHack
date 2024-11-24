package ru.espada.ep.iptip.course;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.course.model.*;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    @PostMapping("attachUser")
    public ResponseEntity<?> attachUserToCourse(@Valid @RequestBody AttachUserToCourseModel attachUserToCourseModel) {
        courseService.attachUserToCourse(
                attachUserToCourseModel.getCourseId(),
                attachUserToCourseModel.getUserId()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("detachUser")
    public ResponseEntity<?> detachUserFromCourse(@Valid @RequestBody DetachUserFromCourseModel detachUserFromCourseModel) {
        courseService.detachUserFromCourse(
                detachUserFromCourseModel.getCourseId(),
                detachUserFromCourseModel.getUserId()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("attachStudyGroup")
    public ResponseEntity<?> attachStudyGroupToCourse(@Valid @RequestBody AttachStudyGroupToCourseModel attachStudyGroupToCourseModel) {
        courseService.attachStudyGroupToCourse(
                attachStudyGroupToCourseModel.getCourseId(),
                attachStudyGroupToCourseModel.getStudyGroupId()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("detachStudyGroup/")
    public ResponseEntity<?> detachStudyGroupFromCourse(@Valid @RequestBody DetachStudyGroupFromCourseModel detachStudyGroupFromCourseModel) {
        courseService.detachStudyGroupFromCourse(
                detachStudyGroupFromCourseModel.getCourseId(),
                detachStudyGroupFromCourseModel.getStudyGroupId()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("userCourses/{userId}")
    public ResponseEntity<?> getUserCourses(@PathVariable Long userId) {
        return ResponseEntity.ok(courseService.getUserCourses(userId));
    }

    @PostMapping("course")
    public ResponseEntity<?> createCourse(Principal principal, @Valid @RequestBody CreateCourseModel createCourseModel) {
        // FIXME: is principal supposed to be here?
        courseService.createCourse(principal, createCourseModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
