package ru.espada.ep.iptip.user.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDto {

    private int date;
    private String dayOfWeek;
    private List<LessonDto> lessons;

}
