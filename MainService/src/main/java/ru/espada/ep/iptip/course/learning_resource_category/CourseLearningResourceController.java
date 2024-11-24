package ru.espada.ep.iptip.course.learning_resource_category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/resource")
public class CourseLearningResourceController {

    private CourseLearningResourceService courseLearningResourceService;

    @Autowired
    public void setCourseLearningResourceService(CourseLearningResourceService courseLearningResourceService) {
        this.courseLearningResourceService = courseLearningResourceService;
    }

}

