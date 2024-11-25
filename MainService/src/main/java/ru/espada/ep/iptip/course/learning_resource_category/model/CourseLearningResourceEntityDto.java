package ru.espada.ep.iptip.course.learning_resource_category.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseLearningResourceEntityDto {

    private Long id;
    private String name;
    private String type;
    private String content;
    private String folderName;

}