package ru.espada.ep.iptip.user.permission;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
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
    @Getter
    @Setter
    private Map<String, Object> permissions;
    @Value("${user.permissions.special:null}")
    @Getter
    private List<String> specialPermissions;

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
        if (specialPermissions != null) permissions.addAll(specialPermissions);
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

    public void addPermissions(String username, List<String> permissions) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user == null) {
            return;
        }
        for (String permission : permissions) {
            UserPermissionEntity userPermissionEntity = UserPermissionEntity.builder()
                    .userId(user.getId())
                    .name(permission)
                    .build();
            userPermissionRepository.save(userPermissionEntity);
        }
    }

    public void removePermission(String username, String permission) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user == null) {
            return;
        }
        userPermissionRepository.deleteUserPermissionEntityByUserIdAndName(user.getId(), permission);
    }

    public void removePermissions(String username, List<String> permissions) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user == null) {
            return;
        }
        for (String permission : permissions) {
            userPermissionRepository.deleteUserPermissionEntityByUserIdAndName(user.getId(), permission);
        }
    }

    public void removeAllPermissions(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user == null) {
            return;
        }
        userPermissionRepository.deleteAllByUserId(user.getId());
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
            // special permission
            if (permissionName.equals("admin")) {
                return true;
            }
            if (permission.startsWith(permissionName + ".")) {
                return true;
            }
        }
        return false;
    }

    public boolean hasParentPermission(String username, String permission) {
        return hasPermission(username, getParentPermission(permission));
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

    private String getParentPermission(String permission) {
        int lastDotIndex = permission.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "admin";
        }
        return permission.substring(0, lastDotIndex);
    }

}
