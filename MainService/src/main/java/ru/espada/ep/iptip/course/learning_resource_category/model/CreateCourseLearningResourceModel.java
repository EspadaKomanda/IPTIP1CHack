package ru.espada.ep.iptip.course.learning_resource_category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourseLearningResourceModel {

    private Long courseId;
    private String name;
    private String type;
    private String content;
    private Long folderId;

}
