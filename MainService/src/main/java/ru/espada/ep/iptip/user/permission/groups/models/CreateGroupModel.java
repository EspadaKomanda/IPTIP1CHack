package ru.espada.ep.iptip.user.permission.groups.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGroupModel {

    private String name;
    private String color;
    private List<String> permissions;
    private List<String> usersCanManage;

}
