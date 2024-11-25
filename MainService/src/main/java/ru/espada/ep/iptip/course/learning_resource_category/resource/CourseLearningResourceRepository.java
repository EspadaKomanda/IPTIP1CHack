package ru.espada.ep.iptip.course.learning_resource_category.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.espada.ep.iptip.course.learning_resource_category.CourseLearningResourceCategoryEntity;

import java.util.List;

@Repository
public interface CourseLearningResourceRepository extends JpaRepository<CourseLearningResourceEntity, Long> {
    List<CourseLearningResourceEntity> findCourseLearningResourceEntitiesByCategory(CourseLearningResourceCategoryEntity category);
}