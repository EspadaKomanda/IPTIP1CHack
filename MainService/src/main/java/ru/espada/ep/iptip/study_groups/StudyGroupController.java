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

    // TODO: add methods: create, add users, remove users, get users, set semester
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
        // TODO: implementation
        return null;
    }

    @DeleteMapping("/studyGroup")
    public ResponseEntity<?> deleteStudyGroup(Principal principal, Long studyGroupId) {
        studyGroupService.deleteStudyGroup(principal, studyGroupId);
        return null;
    }

    @GetMapping("/studyGroup")
    public ResponseEntity<?> getStudyGroup(Principal principal, Long studyGroupId) {
        studyGroupService.getStudyGroup(principal, studyGroupId);
        return null;
    }

    @PostMapping("/attachUser")
    public ResponseEntity<?> attachUserToStudyGroup(Principal principal, @Valid @RequestBody AttachUsersToStudyGroupRequest attachUsersToStudyGroupRequest) {
        studyGroupService.attachUserToStudyGroup(principal, attachUsersToStudyGroupRequest);
        return null;
    }

    @DeleteMapping("/detachUser")
    public ResponseEntity<?> detachUserFromStudyGroup(Principal principal, @Valid @RequestBody DetachUserFromStudyGroupRequest detachUserFromStudyGroupRequest) {
        // TODO: implementation
        return null;
    }

    @PutMapping("/setStudyGroupSemester")
    public ResponseEntity<?> setStudyGroupSemester(Principal principal, @Valid @RequestBody SetStudyGroupMembersSemesterRequest setStudyGroupMembersSemesterRequest) {
        // TODO: implementation
        return null;
    }

    @GetMapping("/getStudyGroupMembers")
    public ResponseEntity<?> getStudyGroupMembers(Principal principal, Long studyGroupId) {
        // TODO: implementation
        return null;
    }

    @Autowired
    public void setStudyGroupService(StudyGroupService studyGroupService) {
        this.studyGroupService = studyGroupService;
    }
}

