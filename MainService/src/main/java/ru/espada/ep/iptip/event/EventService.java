package ru.espada.ep.iptip.event;

import ru.espada.ep.iptip.event.models.requests.CreateEventRequest;
import ru.espada.ep.iptip.event.models.requests.CreateEventForStudyGroupsRequest;
import ru.espada.ep.iptip.event.models.requests.ModifyEventRequest;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;

import java.security.Principal;
import java.util.List;

public interface EventService {
    EventEntity createEvent(Principal principal, CreateEventRequest request);
    EventEntity createEventForStudyGroups(Principal principal, CreateEventForStudyGroupsRequest request);
    EventEntity modifyEvent(Principal principal, ModifyEventRequest request);
    EventEntity getEvent(Principal principal, Long id);
    List<StudyGroupEntity> eventStudyGroups(Principal principal, Long id);
    void deleteEvent(Principal principal, Long id);
}
