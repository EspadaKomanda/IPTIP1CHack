package ru.espada.ep.iptip.course;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.course.model.CreateCourseModel;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    // TODO: add methods: attach study group to course, detach study group from course
    // TODO: add methods: get users courses

    @PostMapping("attachUser/course/{courseId}/user/{userId}")
    public ResponseEntity<?> attachUserToCourse(Principal principal, @PathVariable Long courseId, @PathVariable Long userId) {
        courseService.attachUserToCourse(courseId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("detachUser/course/{courseId}/user/{userId}")
    public ResponseEntity<?> detachUserFromCourse(Principal principal, @PathVariable Long courseId, @PathVariable Long userId) {
        courseService.detachUserFromCourse(courseId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createCourse(Principal principal, @Valid @RequestBody CreateCourseModel createCourseModel) {
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
