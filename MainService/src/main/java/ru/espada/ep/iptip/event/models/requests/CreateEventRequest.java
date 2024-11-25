package ru.espada.ep.iptip.event.models.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEventRequest {

    @NotNull
    private String name;
    @NotNull
    private Long date;
    private int weekday;
    private boolean is_week_event;
    private Long begin_date;
    private Long end_date;
    private Long duration;
}
