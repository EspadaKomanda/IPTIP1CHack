package ru.espada.ep.iptip.user.permission.groups;

import ru.espada.ep.iptip.user.permission.groups.models.AddPermissionRequest;
import ru.espada.ep.iptip.user.permission.groups.models.CreateGroupModel;
import ru.espada.ep.iptip.user.permission.groups.models.GroupToUserRequest;

import java.security.Principal;
import java.util.List;

public interface GroupService {
    List<GroupEntity> getGroups(String username);

    void createGroup(Principal principal, CreateGroupModel createGroupModel);

    void addPermission(AddPermissionRequest addPermissionRequest);

    void giveGroupToUsers(GroupToUserRequest groupToUserRequest);
    void removeGroupFromUser(GroupToUserRequest groupToUserRequest);

    void deleteGroup(String group);
}
