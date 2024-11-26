package ru.espada.ep.iptip.study_groups.study_group_event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupEventRepository extends JpaRepository<StudyGroupEventEntity, Long> {
    StudyGroupEventEntity findByEventIdAndStudyGroupId(Long eventId, Long studyGroupId);

    List<StudyGroupEventEntity> findAllByEventId(Long eventId);
}