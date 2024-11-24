package ru.espada.ep.iptip.course.model;

import lombok.*;
import ru.espada.ep.iptip.audit.Auditable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourseLearningResourceCategoryModel extends Auditable {

    private String name;
    private Long courseId;

}