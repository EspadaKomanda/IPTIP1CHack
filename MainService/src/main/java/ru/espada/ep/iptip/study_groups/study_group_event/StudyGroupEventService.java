package ru.espada.ep.iptip.study_groups.study_group_event;

import ru.espada.ep.iptip.study_groups.study_group_event.model.requests.AttachEventToStudyGroupRequest;
import ru.espada.ep.iptip.study_groups.study_group_event.model.requests.DetachEventFromStudyGroupRequest;

import java.security.Principal;

public interface StudyGroupEventService {
    StudyGroupEventEntity attachEventToStudyGroup(Principal principal, AttachEventToStudyGroupRequest request);

    void detachEventFromStudyGroup(Principal principal, DetachEventFromStudyGroupRequest request);
}
