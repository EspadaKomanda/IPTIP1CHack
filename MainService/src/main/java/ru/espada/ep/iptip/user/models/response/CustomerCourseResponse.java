package ru.espada.ep.iptip.user.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.CourseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCourseResponse {
    private Long id;
    private String name;
    private String description;
}
