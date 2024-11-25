package ru.espada.ep.iptip.user.permission.groups;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupEntityDto {
    private Long id;
    private String name;
    private String color;
    private List<String> groupPermissions;
}