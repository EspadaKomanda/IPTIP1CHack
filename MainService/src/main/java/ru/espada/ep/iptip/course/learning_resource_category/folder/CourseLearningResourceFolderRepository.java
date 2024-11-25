package ru.espada.ep.iptip.course.learning_resource_category.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseLearningResourceFolderRepository extends JpaRepository<CourseLearningResourceFolderEntity, Long> {
    Optional<CourseLearningResourceFolderEntity> findCourseLearningResourceFolderEntityById(Long id);
}