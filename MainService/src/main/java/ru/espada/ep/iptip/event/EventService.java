package ru.espada.ep.iptip.event;

import ru.espada.ep.iptip.event.models.requests.CreateEventRequest;
import ru.espada.ep.iptip.event.models.requests.CreateEventForStudyGroupsRequest;

import java.security.Principal;

public interface EventService {
    public EventEntity createEvent(Principal principal, CreateEventRequest request);
    public EventEntity createEventForStudyGroups(Principal principal, CreateEventForStudyGroupsRequest request);
}
