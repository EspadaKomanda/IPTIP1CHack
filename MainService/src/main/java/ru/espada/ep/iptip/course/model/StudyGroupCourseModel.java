package ru.espada.ep.iptip.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyGroupCourseModel {

    private Long studyGroupId;
    private Long courseId;

}
