package ru.espada.ep.iptip.course.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.user.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_test")
public class UserTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;

    @Column(nullable = false)
    private Long startTime;
    @Column(nullable = false)
    private Long endTime;
    private Long userStartTime;
    private Long userEndTime;

    @Column(nullable = false)
    private TestStatus status;
    private int result;


}
