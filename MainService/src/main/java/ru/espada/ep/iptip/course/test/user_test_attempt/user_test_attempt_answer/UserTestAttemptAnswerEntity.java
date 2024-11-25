package ru.espada.ep.iptip.course.test.user_test_attempt.user_test_attempt_answer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.test.question.QuestionEntity;
import ru.espada.ep.iptip.course.test.user_test_attempt.UserTestAttemptEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_test_attempt_answer")
public class UserTestAttemptAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_test_attempt_id")
    private UserTestAttemptEntity userTestAttempt;
    @OneToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    private String content;
    private TestAttemptAnswerStatus status;
    private Long statusModifiedAt;

}
