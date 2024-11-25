package ru.espada.ep.iptip.user.permission.groups;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.user.permission.groups.permission_group.PermissionGroupEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "p_group")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;

    @OneToMany
    @JoinColumn(name = "group_id")
    private List<PermissionGroupEntity> permissionGroups;

}
