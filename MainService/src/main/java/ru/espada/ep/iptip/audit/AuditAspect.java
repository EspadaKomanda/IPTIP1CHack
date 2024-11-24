package ru.espada.ep.iptip.audit;

import jakarta.persistence.Table;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Field;

@Aspect
@Component
public class AuditAspect {

    private AuditService auditService;

    // Определяем точку соединения для всех методов save и delete в репозиториях
    @Pointcut("execution(* org.springframework.data.repository.CrudRepository+.save(..)) || execution(* org.springframework.data.repository.CrudRepository+.delete(..))")
    public void repositoryMethods() {}

    @Before("repositoryMethods()")
    public void logBeforeRepositoryMethod(JoinPoint joinPoint) {
        Object entity = joinPoint.getArgs()[0];
        if (entity instanceof Auditable auditableEntity) {
            String methodName = joinPoint.getSignature().getName();
            if (methodName.equals("delete")) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String username = userDetails.getUsername();
                auditService.logDelete(auditableEntity, username);
            }
        }
    }

    @After("repositoryMethods()")
    public void logAfterRepositoryMethod(JoinPoint joinPoint) {
        Object entity = joinPoint.getArgs()[0];
        if (entity instanceof Auditable auditableEntity) {
            String methodName = joinPoint.getSignature().getName();
            if (methodName.equals("save")) {
                auditService.logSave(auditableEntity);
            }
        }
    }

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }
}


