package ru.espada.ep.iptip.course.test.course_test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.CourseEntity;
import ru.espada.ep.iptip.course.test.TestEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course_test")
public class CourseTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long startTime;
    private Long endTime;
    private int attempts;
    private Long time;
    private boolean hideResultScore;
    private boolean hideAnswers;
    private boolean hideAnswerCorrectness;
    private int minScore;
    private int maxScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;
    @OneToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;
}
