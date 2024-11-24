package ru.espada.ep.iptip.course.learning_resource_category;

import ru.espada.ep.iptip.course.learning_resource_category.model.CreateCourseLearningResourceCategoryModel;

import java.security.Principal;

public interface CourseLearningResourceService {
    void createCourseLearningResourceCategory(CreateCourseLearningResourceCategoryModel createCourseLearningResourceCategoryModel);
}
