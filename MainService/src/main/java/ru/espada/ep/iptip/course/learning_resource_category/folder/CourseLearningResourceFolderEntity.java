package ru.espada.ep.iptip.course.learning_resource_category.folder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.audit.Auditable;
import ru.espada.ep.iptip.course.learning_resource_category.CourseLearningResourceCategoryEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course_learning_resource_folder")
public class CourseLearningResourceFolderEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "course_learning_resource_category_id")
    private CourseLearningResourceCategoryEntity category;

}