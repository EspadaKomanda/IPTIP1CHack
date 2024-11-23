package ru.espada.ep.iptip.user.permission.groups;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/admin/group")
public class GroupController {

    private GroupService groupService;

    @GetMapping("")
    public ResponseEntity<?> getGroups() {
        return ResponseEntity.ok(groupService.getGroups());
    }

    @Autowired
    public void setGroupServiceImpl(GroupService groupService) {
        this.groupService = groupService;
    }
}
