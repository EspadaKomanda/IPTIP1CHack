package ru.espada.ep.iptip.study_groups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Long> {
    List<StudyGroupEntity> findAllByEventId(Object unknownAttr1);
}