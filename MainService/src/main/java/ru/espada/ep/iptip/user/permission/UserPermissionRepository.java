package ru.espada.ep.iptip.user.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermissionEntity, Long> {

    boolean existsUserPermissionEntityByUserIdAndName(Long userId, String name);
    Optional<Set<UserPermissionEntity>> findAllByUserId(Long userId);

}
