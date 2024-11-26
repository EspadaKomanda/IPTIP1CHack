package ru.espada.ep.iptip.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.audit.Auditable;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "event")
@Permission(value = "event", children = {})
public class EventEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @FieldPermission
    private String name;
    @FieldPermission
    private Long date;
    @FieldPermission
    private int weekday;
    @FieldPermission
    private Boolean is_week_even;
    @FieldPermission
    private Long begin_date;
    @FieldPermission
    private Long end_date;
    @Column(nullable = false)
    @FieldPermission
    private Long duration;
    @Column(length = 128)
    private String type;
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher;
    @Column(columnDefinition = "TEXT")
    private String location;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "study_group_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "study_group_id") )
    private Set<StudyGroupEntity> study_groups;

}
