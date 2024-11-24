package ru.espada.ep.iptip.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.user.models.request.AddRoleRequest;
import ru.espada.ep.iptip.user.models.request.CreateProfileRequest;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/profile")
    public ResponseEntity<?> createProfile(Principal principal, @Valid @RequestBody CreateProfileRequest createProfileRequest) {
        userService.createProfile(principal, createProfileRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Principal principal) {
        return ResponseEntity.ok(userService.getUser(principal.getName()));
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(Principal principal, @RequestBody byte[] avatar) {
        String url = userService.uploadAvatar(principal.getName(), avatar).join();
        return ResponseEntity.ok(url);
    }

    @GetMapping("/avatar")
    public ResponseEntity<?> getAvatarUrl(Principal principal) {
        return ResponseEntity.ok(userService.getAvatarUrl(principal.getName()));
    }

    @GetMapping("/instituteInfo/username/{username}")
    public ResponseEntity<?> getInstituteInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getInstituteInfo(username));
    }

    @GetMapping("/courses/username/{username}")
    public ResponseEntity<?> getMyCourses(@PathVariable String username) {
        return ResponseEntity.ok(userService.getMyCourses(username));
    }
}
