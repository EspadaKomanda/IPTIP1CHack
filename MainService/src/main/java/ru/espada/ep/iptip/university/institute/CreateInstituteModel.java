package ru.espada.ep.iptip.university.institute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateInstituteModel {

    private Long universityId;
    private String name;

}
