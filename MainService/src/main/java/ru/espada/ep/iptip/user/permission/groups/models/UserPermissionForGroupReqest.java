package ru.espada.ep.iptip.user.permission.groups.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPermissionForGroupReqest {

    private String username;
    private String group;

}
