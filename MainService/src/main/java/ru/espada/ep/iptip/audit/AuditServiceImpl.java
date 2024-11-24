package ru.espada.ep.iptip.audit;

import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.audit.entity.AuditEntity;
import ru.espada.ep.iptip.audit.entity.AuditRepository;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {

    private AuditRepository auditRepository;
    @Value("${audit.page-size:10}")
    private int pageSize;

    @Override
    @Async
    public void logSave(Auditable auditableEntity) {
        Table table = auditableEntity.getClass().getAnnotation(Table.class);

        try {
            Field idField = auditableEntity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object id = null;
            try {
                id = idField.get(auditableEntity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            String operation = "UPDATE";
            if (!auditRepository.existsByEntityId((Long) id)) {
                operation = "CREATE";
            }

            AuditEntity auditEntity = AuditEntity.builder()
                    .entityId((Long) id)
                    .inTable(table.name())
                    .event(operation)
                    .who(auditableEntity.getModifiedBy())
                    .time(auditableEntity.getModifiedAt())
                    .build();

            auditRepository.save(auditEntity);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void logDelete(Auditable auditableEntity, String username) {
        Table table = auditableEntity.getClass().getAnnotation(Table.class);
        try {
            Field idField = auditableEntity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object id = null;
            try {
                id = idField.get(auditableEntity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            String operation = "DELETE";

            AuditEntity auditEntity = AuditEntity.builder()
                    .entityId((Long) id)
                    .inTable(table.name())
                    .event(operation)
                    .who(auditableEntity.getModifiedBy())
                    .time(auditableEntity.getModifiedAt())
                    .build();

            auditRepository.save(auditEntity);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AuditEntity> getAudits(int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return auditRepository.findAll(pageable).toList();
    }

    @Autowired
    public void setAuditRepository(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }
}
