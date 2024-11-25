package ru.espada.ep.iptip.course.test.user_test_attempt;

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
@Table(name = "user_test_attempt")
public class UserTestAttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long courseTestId;

    private int attemptNumber;
    private int score;
    private Long date;
    private boolean passed;

}
