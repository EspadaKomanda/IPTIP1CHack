package ru.espada.ep.iptip.user.permission.groups;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.user.permission.groups.models.AddPermissionRequest;
import ru.espada.ep.iptip.user.permission.groups.models.CreateGroupModel;
import ru.espada.ep.iptip.user.permission.groups.models.GroupToUserRequest;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/admin/group")
public class GroupController {

    private GroupService groupService;

    @GetMapping("")
    public ResponseEntity<?> getGroups(Principal principal) {
        return ResponseEntity.ok(groupService.getGroups(principal.getName()));
    }

    @PostMapping("")
    public ResponseEntity<?> createGroup(Principal principal, @Valid @RequestBody CreateGroupModel createGroupModel) {
        groupService.createGroup(principal, createGroupModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{group}")
    @PreAuthorize("hasPermission(#group, 'group.{group}')")
    public ResponseEntity<?> deleteGroup(@PathVariable String group) {
        groupService.deleteGroup(group);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/add-permission")
    @PreAuthorize("hasPermission(#addPermissionRequest, 'group.{group}')")
    public ResponseEntity<?> addPermission(@RequestBody AddPermissionRequest addPermissionRequest) {
        groupService.addPermission(addPermissionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/give")
    @PreAuthorize("hasPermission(#groupToUserRequest, 'group.{group}')")
    public ResponseEntity<?> giveGroup(@RequestBody GroupToUserRequest groupToUserRequest) {
        groupService.giveGroupToUsers(groupToUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/remove")
    @PreAuthorize("hasPermission(#groupToUserRequest, 'group.{group}')")
    public ResponseEntity<?> removeGroup(@RequestBody GroupToUserRequest groupToUserRequest) {
        groupService.removeGroupFromUser(groupToUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Autowired
    public void setGroupServiceImpl(GroupService groupService) {
        this.groupService = groupService;
    }
}
