package ru.espada.ep.iptip.course.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.CourseEntity;
import ru.espada.ep.iptip.course.test.question.QuestionEntity;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "test")
@Permission(children = {QuestionEntity.class}, value = "test")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @FieldPermission
    private String name;
    @FieldPermission
    private int attempts;
    private Long time;
    private boolean hideResultScore;
    private boolean hideAnswers;
    private boolean hideAnswerCorrectness;
    private int minScore;
    private int maxScore;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "test_id")
    @FieldPermission
    private List<QuestionEntity> questions;

}
