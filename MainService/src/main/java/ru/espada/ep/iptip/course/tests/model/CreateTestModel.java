package ru.espada.ep.iptip.course.tests.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTestModel {

    private Long courseId;
    private int position;
    private String name;
    private String description;

}
