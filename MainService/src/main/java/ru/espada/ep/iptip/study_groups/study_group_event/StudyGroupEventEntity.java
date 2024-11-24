package ru.espada.ep.iptip.study_groups.study_group_event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "study_group_event")
public class StudyGroupEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "study_group_id")
    private Long studyGroupId;
    @Column(name = "event_id")
    private Long eventId;
    private boolean isMandatory;

}
