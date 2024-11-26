package ru.espada.ep.iptip.study_groups.study_group_event;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.study_groups.study_group_event.model.requests.AttachEventToStudyGroupRequest;
import ru.espada.ep.iptip.study_groups.study_group_event.model.requests.DetachEventFromStudyGroupRequest;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/studyGroupEvent")
public class StudyGroupEventController {

    private StudyGroupEventService studyGroupEventService;

    @PostMapping("/attachEventToStudyGroup")
    // защищено!
    public ResponseEntity<?> attachEventToStudyGroup(Principal principal, @Valid @RequestBody AttachEventToStudyGroupRequest request) {
        StudyGroupEventEntity studyGroupEvent = studyGroupEventService.attachEventToStudyGroup(principal, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(studyGroupEvent);
    }

    // TODO: deal with the principal stuff in here as well
    // защищено!
    @DeleteMapping("/detachEventFromStudyGroup")
    public ResponseEntity<?> detachEventFromStudyGroup(Principal principal, @Valid @RequestBody DetachEventFromStudyGroupRequest request) {
        studyGroupEventService.detachEventFromStudyGroup(principal, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Autowired
    public void setStudyGroupEventService(StudyGroupEventService studyGroupEventService) {
        this.studyGroupEventService = studyGroupEventService;
    }
}

