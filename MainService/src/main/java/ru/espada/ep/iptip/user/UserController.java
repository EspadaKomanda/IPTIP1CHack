package ru.espada.ep.iptip.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.user.models.request.AddRoleRequest;
import ru.espada.ep.iptip.user.models.request.AttachUserToCourseRequest;
import ru.espada.ep.iptip.user.models.request.CreateCustomerRequest;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add-role")
    public ResponseEntity<?> addRole(@RequestBody AddRoleRequest addRoleRequest) {
        userService.addRole(addRoleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/create-customer")
    public ResponseEntity<?> createCustomer(Principal principal, @Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        userService.createCustomer(principal, createCustomerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-responsible-courses")
    public ResponseEntity<?> getResponsibleCourses(Principal principal) {
        return ResponseEntity.ok(userService.getResponsibleCourses(principal));
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUser(Principal principal) {
        return ResponseEntity.ok(userService.getUser(principal.getName()));
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/get-user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping("/get-users/{page}")
    public ResponseEntity<?> getUsers(@PathVariable int page) {
        return ResponseEntity.ok(userService.allUsers(page));
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(Principal principal, @RequestBody byte[] avatar) {
        String url = userService.uploadAvatar(principal.getName(), avatar).join();
        return ResponseEntity.ok(url);
    }

    @GetMapping("/get-avatar-url")
    public ResponseEntity<?> getAvatarUrl(Principal principal) {
        return ResponseEntity.ok(userService.getAvatarUrl(principal.getName()));
    }

    //@PreAuthorize("hasPermission(#attachUserToCourseRequest, 'course.{courseId}')")
}
