package ru.espada.ep.iptip.user.auth;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.espada.ep.iptip.config.AuthConfig;
import ru.espada.ep.iptip.localization.LocalizationService;
import ru.espada.ep.iptip.security.JwtCore;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.UserService;
import ru.espada.ep.iptip.user.models.request.AuthRequest;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final LocalizationService localizationService;
    @Value("${auth.register.enabled}")
    private boolean registerEnabled;

    public UserAuthController(UserService userService, AuthenticationManager authenticationManager, JwtCore jwtCore, LocalizationService localizationService, AuthConfig authConfig) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtCore = jwtCore;
        this.localizationService = localizationService;
        this.registerEnabled = authConfig.isRegisterEnabled();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException(localizationService.getLocalizedMessage("exception.auth.bad_credentials"));
        }catch (Exception e) {
            throw new RuntimeException("Exception during authentication: " + e.getMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest authRequest) {
        if (!registerEnabled) {
            throw new RuntimeException(localizationService.getLocalizedMessage("exception.auth.register_disabled"));
        }
        userService.saveUser(UserEntity.builder()
                        .username(authRequest.getLogin())
                        .password(authRequest.getPassword())
                        .build());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/aregister")
    @PreAuthorize("hasPermission(#authRequest, 'users.admin')")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> aregister(@Valid @RequestBody AuthRequest authRequest) {
        userService.saveUser(UserEntity.builder()
                        .username(authRequest.getLogin())
                        .password(authRequest.getPassword())
                        .build());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
