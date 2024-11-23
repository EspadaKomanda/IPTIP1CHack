package ru.espada.ep.iptip.university;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.university.institute.InstituteEntity;
import ru.espada.ep.iptip.user.permission.annotation.FieldPermission;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "university")
@Permission(value = "university", children = {InstituteEntity.class}, isStart = true)
public class UniversityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FieldPermission
    private String name;

    @OneToMany(mappedBy = "university")
    @FieldPermission
    private Set<InstituteEntity> institutes;

}
