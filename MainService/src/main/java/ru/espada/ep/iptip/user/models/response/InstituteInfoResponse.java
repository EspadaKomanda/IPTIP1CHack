package ru.espada.ep.iptip.user.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstituteInfoResponse {
    private String major;
    private String majorCode;
    private String faculty;
    private String institute;
    private String university;
    private String studyGroup;
    private Integer semester;
    private Integer course = semester == null ? null : semester / 2;
}