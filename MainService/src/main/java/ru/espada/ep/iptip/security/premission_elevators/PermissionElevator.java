package ru.espada.ep.iptip.security.premission_elevators;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.espada.ep.iptip.user.permission.UserPermissionService;

import java.io.Serializable;
import java.lang.reflect.Field;


@Component
public class PermissionElevator implements PermissionEvaluator {

    private final UserPermissionService userPermissionService;

    public PermissionElevator(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String permissionName = permission.toString();
        if (targetDomainObject != null) {
            for (Field field : targetDomainObject.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    String value = field.get(targetDomainObject).toString();
                    permissionName = permissionName.replace("{" + field.getName() + "}", value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return userPermissionService.hasPermission(authentication.getName(), permissionName);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        /*
        String permissionName = permission.toString();

        Map<String, Object> targetDomainObjects = new HashMap<>();
        // паттерн placeholder:table:entityField
        // разделитель ","
        String[] parts = targetType.split(":");
        for (String part : parts) {
            String[] date = part.split(",");
            JpaRepository<?, ?> repository = tableHelperService.getRepository(date[1]);
            Class<?> entityClass = tableHelperService.getEntityClass(date[1]);
        }


        for (Field field : targetDomainObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String value = field.get(targetDomainObject).toString();
                permissionName = permissionName.replace("{" + field.getName() + "}", value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return userPermissionService.hasPermission(authentication.getName(), permissionName);

         */
        return false;
    }
}
