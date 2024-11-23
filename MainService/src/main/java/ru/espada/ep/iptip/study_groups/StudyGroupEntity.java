package ru.espada.ep.iptip.study_groups;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.event.EventEntity;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "study_group")
@Permission(children = {UserEntity.class}, value = "study_group", isStart = true)
public class StudyGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @FieldPermission
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_study_group",
            joinColumns = @JoinColumn(name = "study_group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @FieldPermission
    private Set<UserEntity> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "study_group_event",
            joinColumns = @JoinColumn(name = "study_group_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    @FieldPermission
    private Set<EventEntity> events;

}