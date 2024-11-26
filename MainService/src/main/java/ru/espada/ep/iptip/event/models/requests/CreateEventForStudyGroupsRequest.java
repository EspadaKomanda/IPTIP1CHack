package ru.espada.ep.iptip.event.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEventForStudyGroupsRequest {
    // TODO: implement request model
    @NotNull
    private String name;
    @NotNull
    private Long date;
    private int weekday;
    private boolean is_week_event;
    private Long begin_date;
    private Long end_date;
    private Long duration;
    private List<Long> study_group_ids;
}
