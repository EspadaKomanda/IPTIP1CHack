package ru.espada.ep.iptip.course.learning_resource_category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.espada.ep.iptip.course.CourseEntity;

import java.util.List;

@Repository
public interface CourseLearningResourceCategoryRepository extends JpaRepository<CourseLearningResourceCategoryEntity, Long> {
    List<CourseLearningResourceCategoryEntity> findCourseLearningResourceCategoryEntitiesByCourse(CourseEntity course);
}