package ru.espada.ep.iptip.study_groups.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudyGroupRequest {
    private String name;
    private Long facultyId;
}