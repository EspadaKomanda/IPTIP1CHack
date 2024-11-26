package ru.espada.ep.iptip.course.test.question.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuestionModel {

    private int position;
    private String title;
    private String questionType;
    private String content;

}
