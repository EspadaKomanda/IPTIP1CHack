package ru.espada.ep.iptip.course;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseTestEntityDto {
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
}
