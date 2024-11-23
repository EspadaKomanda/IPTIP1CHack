package ru.espada.ep.iptip.user.application_event;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.UserRepository;
import ru.espada.ep.iptip.user.permission.UserPermissionEntity;
import ru.espada.ep.iptip.user.permission.UserPermissionRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private PasswordEncoder bCryptPasswordEncoder;
    @Value("${admin.username:admin}")
    private String adminUsername;
    @Value("${admin.password:admin}")
    private String adminPassword;
    private UserPermissionRepository userPermissionRepository;

    public AdminDataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // create admin user
        if (userRepository.existsByUsername(adminUsername)) {
            return;
        }

        UserEntity admin = UserEntity.builder()
                .username(adminUsername)
                .password(bCryptPasswordEncoder.encode(adminPassword))
                .build();

        userRepository.save(admin);

        // права
        UserPermissionEntity adminPermission = UserPermissionEntity.builder()
                .userId(admin.getId())
                .name("users.admin")
                .startTime(-1L)
                .endTime(-1L)
                .build();

        userPermissionRepository.save(adminPermission);
    }

    @Autowired
    public void setBCryptPasswordEncoder(PasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setUserPermissionRepository(UserPermissionRepository userPermissionRepository) {
        this.userPermissionRepository = userPermissionRepository;
    }
}
