package ru.espada.ep.iptip.audit;

import ru.espada.ep.iptip.audit.entity.AuditEntity;

import java.util.List;

public interface AuditService {
    void logSave(Auditable entity);

    void logDelete(Auditable auditableEntity, String username);

    List<AuditEntity> getAudits(int page);
}
