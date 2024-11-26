package ru.espada.ep.iptip.study_groups.study_group_event.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetachEventFromStudyGroupRequest {
    private long studyGroupId;
    private long eventId;
}
