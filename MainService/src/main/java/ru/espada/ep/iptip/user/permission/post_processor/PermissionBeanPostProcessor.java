package ru.espada.ep.iptip.user.permission.post_processor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class PermissionBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext applicationContext;

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (beanName.equals("userPermissionService")) {
            Map<String, Class<?>> beansWithPermission = new HashMap<>();
            Set<EntityType<?>> entities = applicationContext.getBean(EntityManager.class).getMetamodel().getEntities();
            for (EntityType<?> entity : entities) {
                Permission permission = entity.getJavaType().getAnnotation(Permission.class);
                if (permission != null) {
                    beansWithPermission.put(permission.value(), entity.getJavaType());
                }
            }
            Field field = null;
            try {
                field = bean.getClass().getDeclaredField("permissions");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(true);
            try {
                field.set(bean, getPermissions(beansWithPermission));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return bean;
    }

    private Map<String, Object> getPermissions(Map<String, Class<?>> beansWithPermission) {
        Map<String, Object> permissions = new HashMap<>();
        List<Class<?>> beansWithNoRelation = beansWithPermission.values().stream().filter(bean -> bean.getAnnotation(Permission.class).isStart()).toList();
        for (Class<?> bean : beansWithNoRelation) {
            permissions.put(bean.getAnnotation(Permission.class).value(), getPermissions(bean));
        }
        return permissions;
    }

    private Map<String, Object> getPermissions(Class<?> bean) {
        Map<String, Object> permissions = new HashMap<>();
        for (Field field : bean.getDeclaredFields()) {
            FieldPermission annotation = field.getAnnotation(FieldPermission.class);
            if (annotation != null) {
                String name = field.getName();
                if (!annotation.value().isEmpty()) {
                    name = annotation.value();
                }
                permissions.put(name, null);
            }
        }
        Permission permission = bean.getAnnotation(Permission.class);
        for (Class<?> child : permission.children()) {
            permissions.put(child.getAnnotation(Permission.class).value(), getPermissions(child));
        }
        return permissions;
    }
}
