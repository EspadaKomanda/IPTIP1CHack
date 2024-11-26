package ru.espada.ep.iptip.user.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonDto {

    private String subject;
    private String type;
    private String time;
    private String location;
    private String teacher;
    private int lessonNumber;
    private boolean mandatory;

}
