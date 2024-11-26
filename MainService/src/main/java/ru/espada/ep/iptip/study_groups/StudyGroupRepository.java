package ru.espada.ep.iptip.study_groups;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Long> {
    // This is utterly retarded solution but it will do for now
    @Query("SELECT sg FROM StudyGroupEntity sg JOIN sg.events e WHERE e.id = :eventId")
    List<StudyGroupEntity> findAllByEventId(@Param("eventId") Long eventId);
}