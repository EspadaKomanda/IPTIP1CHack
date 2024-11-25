package ru.espada.ep.iptip.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.course.CourseFullDto;
import ru.espada.ep.iptip.course.model.CourseEntityDto;
import ru.espada.ep.iptip.user.models.request.CreateProfileRequest;
import ru.espada.ep.iptip.user.models.response.InstituteInfoResponse;

import java.security.Principal;
import java.util.List;

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
    public ResponseEntity<UserDto> getUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserDto(principal.getName()));
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDto(id));
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserDto(username));
    }

    @PostMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(Principal principal, @RequestBody byte[] avatar) {
        String url = userService.uploadAvatar(principal.getName(), avatar).join();
        return ResponseEntity.ok(url);
    }

    @GetMapping("/avatar")
    public ResponseEntity<String> getAvatarUrl(Principal principal) {
        return ResponseEntity.ok(userService.getAvatarUrl(principal.getName()));
    }

    @GetMapping("/instituteInfo/username/{username}")
    public ResponseEntity<InstituteInfoResponse> getInstituteInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getInstituteInfo(username));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseEntityDto>> getUserCourses(Principal principal) {
        return ResponseEntity.ok(userService.getUserCourses(principal.getName()));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<CourseFullDto> getCourse(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(userService.getCourseFullDto(principal, id));
    }
}
