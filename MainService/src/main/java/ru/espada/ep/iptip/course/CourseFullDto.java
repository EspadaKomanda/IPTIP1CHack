package ru.espada.ep.iptip.course;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseFullDto {

    private Date createdAt;
    private Long id;
    private String name;
    private String description;
    private Set<CourseTestEntityDto> tests;
    private Set<TeacherEntityDto> teachers;

}