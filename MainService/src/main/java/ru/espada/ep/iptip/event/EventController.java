package ru.espada.ep.iptip.event;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.event.models.requests.CreateEventForStudyGroupsRequest;
import ru.espada.ep.iptip.event.models.requests.CreateEventRequest;
import ru.espada.ep.iptip.event.models.requests.ModifyEventRequest;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;

import java.security.Principal;
import java.util.List;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    @PostMapping("/event")
    public ResponseEntity<?> createEvent(Principal principal, @Valid @RequestBody CreateEventRequest request) {
        EventEntity eventEntity = eventService.createEvent(principal, request);
        return ResponseEntity.ok().body(eventEntity);
    }

    @PostMapping("/eventForStudyGroups")
    public ResponseEntity<?> createEventForStudyGroups(Principal principal, @Valid @RequestBody CreateEventForStudyGroupsRequest request) {
        EventEntity eventEntity = eventService.createEventForStudyGroups(principal, request);
        return ResponseEntity.ok().body(eventEntity);
    }

    @PatchMapping("/event")
    public ResponseEntity<?> modifyEvent(Principal principal, @Valid @RequestBody ModifyEventRequest request) {
        EventEntity eventEntity = eventService.modifyEvent(principal, request);
        return ResponseEntity.ok().body(eventEntity);
    }

    @DeleteMapping("/event")
    public ResponseEntity<?> deleteEvent(Principal principal, Long id) {
        eventService.deleteEvent(principal, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEvent(Principal principal, @PathVariable Long id) {
        EventEntity eventEntity = eventService.getEvent(principal, id);
        return ResponseEntity.ok().body(eventEntity);
    }

    @GetMapping("/event/{id}/studyGroups")
    public ResponseEntity<?> eventStudyGroups(Principal principal, @PathVariable Long id) {
        List<StudyGroupEntity> studyGroups = eventService.eventStudyGroups(principal, id);
        return ResponseEntity.ok().body(studyGroups);
    }

    @GetMapping("/event/studyGroup/{studyGroupId}")
    public ResponseEntity<?> getEventsForStudyGroup(Principal principal, @PathVariable Long studyGroupId) {
        // TODO: implementation
        return ResponseEntity.ok().build();
    }

    @GetMapping("/event/studyGroup/{studyGroupId}/upcoming")
    public ResponseEntity<?> getUpcomingEventsForStudyGroup(Principal principal, @PathVariable Long studyGroupId) {
        // TODO: implementation
        return ResponseEntity.ok().build();
    }

    @GetMapping("/event/studyGroup/{studyGroupId}/from/{from}/to/{to}")
    public ResponseEntity<?> getEventsForStudyGroup(Principal principal, @PathVariable Long studyGroupId, @PathVariable Long from, @PathVariable Long to) {
        // TODO: implementation
        return ResponseEntity.ok().build();
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}

