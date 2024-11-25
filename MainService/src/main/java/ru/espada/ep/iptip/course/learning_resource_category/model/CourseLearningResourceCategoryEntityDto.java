package ru.espada.ep.iptip.course.learning_resource_category.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseLearningResourceCategoryEntityDto {
    private Long id;
    private String name;
}