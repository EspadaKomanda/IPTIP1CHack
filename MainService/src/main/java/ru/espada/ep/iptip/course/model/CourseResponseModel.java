package ru.espada.ep.iptip.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.test.TestEntity;
import ru.espada.ep.iptip.user.UserEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponseModel {

    private Long id;

    private String name;
    private UserEntity mainResponsible;
    private Long duration;
    private List<TestEntity> tests;

}
