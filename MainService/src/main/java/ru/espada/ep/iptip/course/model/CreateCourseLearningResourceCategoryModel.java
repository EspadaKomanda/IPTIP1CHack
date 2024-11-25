package ru.espada.ep.iptip.course.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourseLearningResourceCategoryModel {

    private String name;
    private Long courseId;

}