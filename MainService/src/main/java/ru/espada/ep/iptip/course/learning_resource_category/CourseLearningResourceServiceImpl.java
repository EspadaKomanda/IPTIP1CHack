package ru.espada.ep.iptip.course.learning_resource_category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.course.learning_resource_category.model.CreateCourseLearningResourceCategoryModel;

@Service
public class CourseLearningResourceServiceImpl implements CourseLearningResourceService {

    private CourseLearningResourceCategoryRepository courseLearningResourceCategoryRepository;

    @Override
    public void createCourseLearningResourceCategory(CreateCourseLearningResourceCategoryModel createCourseLearningResourceCategoryModel) {

    }

    @Autowired
    public void setCourseLearningResourceCategoryRepository(CourseLearningResourceCategoryRepository courseLearningResourceCategoryRepository) {
        this.courseLearningResourceCategoryRepository = courseLearningResourceCategoryRepository;
    }
}
