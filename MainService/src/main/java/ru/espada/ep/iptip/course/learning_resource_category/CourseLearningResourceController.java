package ru.espada.ep.iptip.course.learning_resource_category;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.espada.ep.iptip.course.learning_resource_category.model.CreateCourseLearningResourceCategoryModel;

import java.security.Principal;

@RestController
@RequestMapping("/course/resource")
public class CourseLearningResourceController {

    private CourseLearningResourceService courseLearningResourceService;

    @PostMapping("/category")
    public ResponseEntity<?> createCourseLearningResourceCategory(@Valid @RequestBody CreateCourseLearningResourceCategoryModel createCourseLearningResourceCategoryModel) {
        courseLearningResourceService.createCourseLearningResourceCategory(createCourseLearningResourceCategoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Autowired
    public void setCourseLearningResourceService(CourseLearningResourceService courseLearningResourceService) {
        this.courseLearningResourceService = courseLearningResourceService;
    }

}

