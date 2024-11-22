package ru.espada.ep.iptip.user.permission;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.UserRepository;
import ru.espada.ep.iptip.user.UserService;

import java.util.*;

@Service
public class UserPermissionService {

    private final UserPermissionRepository userPermissionRepository;
    private final UserRepository userRepository;
    private Map<String, Object> permissions;

    public UserPermissionService(UserPermissionRepository userPermissionRepository, UserRepository userRepository) {
        this.userPermissionRepository = userPermissionRepository;
        this.userRepository = userRepository;
    }

    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        for (String key : this.permissions.keySet()) {
            permissions.add(key);
            if (this.permissions.get(key) instanceof String) {
                permissions.add(key + "." + this.permissions.get(key));
            } else {
                Map<String, Object> map = (Map<String, Object>) this.permissions.get(key);
                addPermissionsRecursive(map, key, permissions);
            }
        }
        return permissions;
    }

    public void addPermission(String username, String permission, Long startTime, Long endTime) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user == null) {
            return;
        }
        UserPermissionEntity userPermissionEntity = UserPermissionEntity.builder()
                .userId(user.getId())
                .name(permission)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        userPermissionRepository.save(userPermissionEntity);
    }

    public boolean hasPermission(String username, String permission) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user == null) {
            return false;
        }
        String[] parts = permission.split("\\.");
        if (userPermissionRepository.existsUserPermissionEntityByUserIdAndName(user.getId(), permission)) return true;

        String fullPermission = String.join(".", Arrays.copyOfRange(parts, 0, parts.length - 1));
        if (userPermissionRepository.existsUserPermissionEntityByUserIdAndName(user.getId(), fullPermission)) return true;

        List<String> permissions = userPermissionRepository.findAllByUserId(user.getId()).orElse(new HashSet<>()).stream().map(UserPermissionEntity::getName).toList();
        for (String permissionName : permissions) {
            if (permission.startsWith(permissionName + ".")) {
                return true;
            }
        }
        return false;
    }

    private void addPermissionsRecursive(Map<String, Object> permissions, String permission, List<String> result) {
        for (String key : permissions.keySet()) {
            result.add(permission + "." + key);
            if (permissions.get(key) == null) {
                continue;
            }
            if (permissions.get(key) instanceof String) {
                result.add(permission + "." + key + "." + permissions.get(key));
            } else {
                Map<String, Object> map = (Map<String, Object>) permissions.get(key);
                addPermissionsRecursive(map, permission + "." + key, result);
            }
        }
    }

}
