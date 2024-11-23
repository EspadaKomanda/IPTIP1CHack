package ru.espada.ep.iptip.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "event")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private Long date;
    private int weekday;
    private boolean is_week_event;
    private Long begin_date;
    private Long end_date;
    @Column(nullable = false)
    private Long duration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "study_group_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "study_group_id") )
    @FieldPermission
    private Set<StudyGroupEntity> study_groups;

}
