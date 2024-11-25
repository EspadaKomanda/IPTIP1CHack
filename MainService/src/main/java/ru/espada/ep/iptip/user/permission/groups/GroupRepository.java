package ru.espada.ep.iptip.user.permission.groups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    Optional<GroupEntity> findGroupEntityByName(String group);

    void deleteGroupEntityByName(String group);
}
