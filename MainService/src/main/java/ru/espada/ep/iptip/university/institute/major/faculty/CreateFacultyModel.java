package ru.espada.ep.iptip.university.institute.major.faculty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFacultyModel {

    private String name;
    private Long majorId;

}
