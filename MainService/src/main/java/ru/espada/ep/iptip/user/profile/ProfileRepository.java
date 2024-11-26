package ru.espada.ep.iptip.user.profile;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    
}
