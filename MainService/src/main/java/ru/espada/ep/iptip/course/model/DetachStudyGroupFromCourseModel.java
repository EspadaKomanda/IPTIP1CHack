package ru.espada.ep.iptip.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetachStudyGroupFromCourseModel {
    private Long studyGroupId;
    private Long courseId;
}