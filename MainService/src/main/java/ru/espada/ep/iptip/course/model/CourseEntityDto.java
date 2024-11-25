package ru.espada.ep.iptip.course.model;

import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseEntityDto {

    private Date createdAt;
    private Long id;
    private String name;
    private String description;

}