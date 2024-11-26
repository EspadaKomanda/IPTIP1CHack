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
public class AddPermissionsToGroup {

    private String group;
    private List<String> permissions;

}
