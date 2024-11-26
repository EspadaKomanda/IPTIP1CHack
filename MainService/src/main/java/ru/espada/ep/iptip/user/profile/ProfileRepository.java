package ru.espada.ep.iptip.user.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    ProfileEntity findByUserId(Object unknownAttr1);
}
