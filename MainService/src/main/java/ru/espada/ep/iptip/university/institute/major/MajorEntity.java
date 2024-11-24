package ru.espada.ep.iptip.university.institute.major;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.audit.Auditable;
import ru.espada.ep.iptip.university.institute.InstituteEntity;
import ru.espada.ep.iptip.university.institute.major.faculty.FacultyEntity;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "major")
@Permission(value = "major", children = {FacultyEntity.class})
public class MajorEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FieldPermission
    private String name;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private InstituteEntity institute;

    @OneToMany(mappedBy = "major")
    @FieldPermission
    private List<FacultyEntity> faculties;

    private String majorCode;
}