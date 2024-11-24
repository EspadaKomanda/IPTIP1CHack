package ru.espada.ep.iptip.course.learning_resource_category.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLearningResourceRepository extends JpaRepository<CourseLearningResourceEntity, Long> {
}