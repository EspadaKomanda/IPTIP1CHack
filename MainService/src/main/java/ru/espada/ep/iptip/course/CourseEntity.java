package ru.espada.ep.iptip.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.test.TestEntity;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course")
@Permission(children = {TestEntity.class}, value = "course", isStart = true)
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @FieldPermission
    private String name;
    @FieldPermission
    private String icon_image;
    @OneToOne
    @JoinColumn(name = "main_responsible_id")
    @FieldPermission
    private UserEntity mainResponsible;
    @Column(nullable = false)
    @FieldPermission
    private Long duration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> user;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @FieldPermission
    private Set<TestEntity> tests;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @FieldPermission
    private Set<UserEntity> teachers;

}
