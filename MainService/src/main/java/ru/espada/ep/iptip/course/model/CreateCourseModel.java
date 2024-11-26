package ru.espada.ep.iptip.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourseModel {

    private Long UniversityId;
    private Long InstituteId;
    private Long MajorId;
    private Long FacultyId;
    private String name;
    private String description;

}
