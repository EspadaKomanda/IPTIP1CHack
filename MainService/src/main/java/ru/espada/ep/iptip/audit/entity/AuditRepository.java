package ru.espada.ep.iptip.audit.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
    boolean existsByEntityId(Long id);
}