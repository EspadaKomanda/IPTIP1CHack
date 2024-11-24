package ru.espada.ep.iptip.course.learning_resource_category.resource;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.audit.Auditable;
import ru.espada.ep.iptip.course.learning_resource_category.CourseLearningResourceCategoryEntity;
import ru.espada.ep.iptip.course.learning_resource_category.folder.CourseLearningResourceFolderEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course_learning_resource")
public class CourseLearningResourceEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String content;

    @ManyToOne
    @JoinColumn(name = "course_learning_resource_category_id")
    private CourseLearningResourceCategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "course_learning_resource_folder_id")
    private CourseLearningResourceFolderEntity folder;

}
