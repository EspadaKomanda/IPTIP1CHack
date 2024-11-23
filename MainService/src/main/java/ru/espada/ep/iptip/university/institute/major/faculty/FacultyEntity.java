package ru.espada.ep.iptip.university.institute.major.faculty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.CourseEntity;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;
import ru.espada.ep.iptip.university.institute.major.MajorEntity;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "faculty")
@Permission(value = "faculty", children = {CourseEntity.class, StudyGroupEntity.class})
public class FacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FieldPermission
    private String name;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private MajorEntity major;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "faculty_course",
            joinColumns = @JoinColumn(name = "faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @FieldPermission
    private Set<CourseEntity> courses;

}
