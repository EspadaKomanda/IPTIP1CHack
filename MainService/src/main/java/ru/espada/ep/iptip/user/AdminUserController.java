package ru.espada.ep.iptip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.user.models.request.AddRoleRequest;

import java.security.Principal;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    private UserService userService;

    @PostMapping("/add-role")
    public ResponseEntity<?> addRole(Principal principal, @RequestBody AddRoleRequest addRoleRequest) {
        userService.addRole(principal.getName(), addRoleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-users/{page}")
    @PreAuthorize("hasPermission(#page, 'users.admin')")
    public ResponseEntity<?> getUsers(@PathVariable int page) {
        return ResponseEntity.ok(userService.allUsers(page));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
