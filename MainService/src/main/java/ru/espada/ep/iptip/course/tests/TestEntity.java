package ru.espada.ep.iptip.course.tests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.CourseEntity;
import ru.espada.ep.iptip.course.tests.question.QuestionEntity;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "test", schema = "tests")
@Permission(children = {QuestionEntity.class}, value = "test")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Какой по счёту тест в курсе */
    @FieldPermission
    private int position;
    @Column(nullable = false)
    @FieldPermission
    private String name;
    @FieldPermission
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "test_id")
    @FieldPermission
    private List<QuestionEntity> questions;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private CourseEntity course;

}
