package ru.espada.ep.iptip.study_groups.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachUsersToStudyGroupRequest {
    private Long studyGroupId;
    private List<Long> userIds;
}
