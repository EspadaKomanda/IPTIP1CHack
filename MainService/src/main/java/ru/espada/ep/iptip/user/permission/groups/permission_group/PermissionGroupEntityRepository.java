package ru.espada.ep.iptip.user.permission.groups.permission_group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionGroupEntityRepository extends JpaRepository<PermissionGroupEntity, Long> {
}