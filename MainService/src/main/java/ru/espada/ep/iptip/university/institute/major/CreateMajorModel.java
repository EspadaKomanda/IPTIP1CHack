package ru.espada.ep.iptip.university.institute.major;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMajorModel {

    private String name;
    private Long instituteId;

}
