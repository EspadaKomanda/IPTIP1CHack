package ru.espada.ep.iptip.study_groups.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStudyGroupEntityRepository extends JpaRepository<UserStudyGroupEntity, Long> {
    List<UserStudyGroupEntity> findUserStudyGroupEntitiesByUserId(Long userId);

    UserStudyGroupEntity findUserStudyGroupEntitiesByUserIdAndMain(Long userId, boolean main);
}