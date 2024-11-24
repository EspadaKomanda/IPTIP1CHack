package ru.espada.ep.iptip.course.test.question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.test.TestEntity;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "question")
@Permission(children = {}, value = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @FieldPermission
    private int position;
    @Column(nullable = false)
    @FieldPermission
    private String title;
    @Column(nullable = false)
    @FieldPermission
    private QuestionType questionType;
    @Column(nullable = false)
    @FieldPermission
    private String content;
    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;

}
