package ru.espada.ep.iptip.user.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachUserToCourseRequest {

    private String username;
    private Long courseId;
    private Long startTime;
    private Long endTime;

}
