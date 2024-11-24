package ru.espada.ep.iptip.course.learning_resource_category;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseLearningResourceServiceImpl implements CourseLearningResourceService {

    private CourseLearningResourceCategoryRepository courseLearningResourceCategoryRepository;



    @Autowired
    public void setCourseLearningResourceCategoryRepository(CourseLearningResourceCategoryRepository courseLearningResourceCategoryRepository) {
        this.courseLearningResourceCategoryRepository = courseLearningResourceCategoryRepository;
    }
}
