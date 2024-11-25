package ru.espada.ep.iptip.user.permission.groups;

import ru.espada.ep.iptip.user.permission.groups.models.AddPermissionsToGroup;
import ru.espada.ep.iptip.user.permission.groups.models.UserPermissionForGroupReqest;
import ru.espada.ep.iptip.user.permission.groups.models.CreateGroupModel;
import ru.espada.ep.iptip.user.permission.groups.models.GroupToUserRequest;

import java.security.Principal;
import java.util.List;

public interface GroupService {
    List<GroupEntityDto> getGroups(String username);

    void createGroup(Principal principal, CreateGroupModel createGroupModel);

    void addUserPermission(UserPermissionForGroupReqest userPermissionForGroupReqest);

    void giveGroupToUsers(GroupToUserRequest groupToUserRequest);
    void removeGroupFromUser(GroupToUserRequest groupToUserRequest);

    void deleteGroup(String group);

    void addGroupPermission(Principal principal, AddPermissionsToGroup addPermissionsToGroup);

    GroupEntityDto getGroup(Long id);
}
