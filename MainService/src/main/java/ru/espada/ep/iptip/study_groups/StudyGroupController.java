package ru.espada.ep.iptip.study_groups;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.study_groups.models.requests.*;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/studyGroup")
public class StudyGroupController {

    private StudyGroupService studyGroupService;

    @PostMapping("/studyGroup")
    public ResponseEntity<Long> createStudyGroup(Principal principal, @Valid @RequestBody CreateStudyGroupRequest createStudyGroupRequest) {
        Long id = studyGroupService.createStudyGroup(principal, createStudyGroupRequest).getId();
        return ResponseEntity.ok(id);
    }

    @PostMapping("/studyGroupWithUsers")
    public ResponseEntity<Long> createStudyGroupWithUsers(Principal principal, @Valid @RequestBody CreateStudyGroupWithUsersRequest createStudyGroupWithUsersRequest) {
        Long id = studyGroupService.createStudyGroupWithUsers(principal, createStudyGroupWithUsersRequest).getId();
        return ResponseEntity.ok(id);
    }

    @PatchMapping("/studyGroup")
    public ResponseEntity<?> modifyStudyGroup(Principal principal, @Valid @RequestBody ModifyStudyGroupRequest modifyStudyGroupRequest) {
        studyGroupService.modifyStudyGroup(principal, modifyStudyGroupRequest);
        return null;
    }

    @DeleteMapping("/studyGroup")
    public ResponseEntity<?> deleteStudyGroup(Principal principal, Long studyGroupId) {
        studyGroupService.deleteStudyGroup(principal, studyGroupId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/studyGroup")
    public ResponseEntity<StudyGroupEntity> getStudyGroup(Principal principal, Long studyGroupId) {
        return ResponseEntity.ok().body(studyGroupService.getStudyGroup(principal, studyGroupId));
    }

    @PostMapping("/attachUser")
    public ResponseEntity<?> attachUserToStudyGroup(Principal principal, @Valid @RequestBody AttachUsersToStudyGroupRequest attachUsersToStudyGroupRequest) {
        studyGroupService.attachUserToStudyGroup(principal, attachUsersToStudyGroupRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/detachUser")
    public ResponseEntity<?> detachUserFromStudyGroup(Principal principal, @Valid @RequestBody DetachUserFromStudyGroupRequest detachUserFromStudyGroupRequest) {
        studyGroupService.detachUserFromStudyGroup(principal, detachUserFromStudyGroupRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/setStudyGroupSemester")
    public ResponseEntity<?> setStudyGroupSemester(Principal principal, @Valid @RequestBody SetStudyGroupMembersSemesterRequest setStudyGroupMembersSemesterRequest) {
        studyGroupService.setStudyGroupMembersSemester(principal, setStudyGroupMembersSemesterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getStudyGroupMembers")
    public ResponseEntity<?> getStudyGroupMembers(Principal principal, Long studyGroupId) {
        return ResponseEntity.ok().body(studyGroupService.getStudyGroupMembers(principal, studyGroupId));
    }

    @Autowired
    public void setStudyGroupService(StudyGroupService studyGroupService) {
        this.studyGroupService = studyGroupService;
    }
}

