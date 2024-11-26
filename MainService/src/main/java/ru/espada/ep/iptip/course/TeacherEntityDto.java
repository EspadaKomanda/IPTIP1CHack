package ru.espada.ep.iptip.course;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherEntityDto {

    private Long id;
    private String icon;
    private String name;
    private String surname;
    private String patronymic;

}