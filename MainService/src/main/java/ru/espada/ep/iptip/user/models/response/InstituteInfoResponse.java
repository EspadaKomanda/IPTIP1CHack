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
    public String Major;
    public String MajorCode;
    public String Faculty;
    public String Institute;
    public String University;
    // TODO: integrate group
    public String Group = "Not implemented";
    public Integer Semester;
    public Integer Course = Semester == null ? null : Semester / 2;
}