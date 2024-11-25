package ru.espada.ep.iptip.study_groups.study_group_event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGroupEventRepository extends JpaRepository<StudyGroupEventEntity, Long> {
}