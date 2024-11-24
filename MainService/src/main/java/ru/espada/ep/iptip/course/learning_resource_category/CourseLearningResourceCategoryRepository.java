package ru.espada.ep.iptip.course.learning_resource_category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLearningResourceCategoryRepository extends JpaRepository<CourseLearningResourceCategoryEntity, Long> {
}