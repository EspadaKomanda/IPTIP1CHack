package ru.espada.ep.iptip.audit;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.espada.ep.iptip.audit.entity.AuditEntity;

import java.util.List;

@RestController
@RequestMapping("/audit")
@SecurityRequirement(name = "JWT")
public class AuditController {


    private AuditService auditService;

    @GetMapping("/{page}")
    @PreAuthorize("hasPermission(null, 'audit.view')")
    public List<AuditEntity> getAudits(@PathVariable int page) {
        return auditService.getAudits(page);
    }

    @Autowired
    public void setAuditServiceImpl(AuditService auditService) {
        this.auditService = auditService;
    }
}

