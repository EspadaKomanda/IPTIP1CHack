package ru.espada.ep.iptip.user.permission.groups;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.user.UserService;
import ru.espada.ep.iptip.user.permission.UserPermissionService;
import ru.espada.ep.iptip.user.permission.groups.models.AddPermissionRequest;
import ru.espada.ep.iptip.user.permission.groups.models.CreateGroupModel;
import ru.espada.ep.iptip.user.permission.groups.models.GroupToUserRequest;
import ru.espada.ep.iptip.user.permission.groups.permission_group.PermissionGroupEntity;

import java.security.Principal;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private UserService userService;
    private UserPermissionService userPermissionService;

    @PostConstruct
    public void init() {
        initPermission();
    }

    public void addGroup(GroupEntity groupEntity) {
        groupRepository.save(groupEntity);
        initPermission();
    }

    @Override
    public List<GroupEntity> getGroups(String username) {
        return groupRepository.findAll().stream().filter(groupEntity -> userPermissionService.hasPermission(username, "group." + groupEntity.getName())).toList();
    }

    @Override
    public void createGroup(Principal principal, CreateGroupModel createGroupModel) {
        for (String permission : createGroupModel.getPermissions()) {
            if (!userPermissionService.hasPermission(principal.getName(), permission)) {
                throw new RuntimeException("User " + principal.getName() + " doesn't have permission " + permission);
            }
        }

        GroupEntity groupEntity = GroupEntity.builder()
                .name(createGroupModel.getName())
                .color(createGroupModel.getColor())
                .permission_groups(createGroupModel.getPermissions().stream().map(permission -> new PermissionGroupEntity(permission)).toList())
                .build();
        addGroup(groupEntity);

        for (String username : createGroupModel.getUsersCanManage()) {
            userPermissionService.addPermission(username, "group." + createGroupModel.getName(), -1L, -1L);
        }
    }

    @Override
    public void deleteGroup(String group) {
        groupRepository.deleteGroupEntityByName(group);
    }

    @Override
    public void addPermission(AddPermissionRequest addPermissionRequest) {
        userPermissionService.addPermission(addPermissionRequest.getUsername(), "group." + addPermissionRequest.getGroup(), -1L, -1L);
    }

    @Override
    public void giveGroupToUsers(GroupToUserRequest groupToUserRequest) {
        GroupEntity groupEntity = groupRepository.findGroupEntityByName(groupToUserRequest.getGroup()).orElseThrow();

        for (String username : groupToUserRequest.getUsers()) {
            userPermissionService.addPermissions(username, groupEntity.getPermission_groups().stream().map(PermissionGroupEntity::getPermission).toList());
        }
    }

    @Override
    public void removeGroupFromUser(GroupToUserRequest groupToUserRequest) {
        GroupEntity groupEntity = groupRepository.findGroupEntityByName(groupToUserRequest.getGroup()).orElseThrow();

        for (String username : groupToUserRequest.getUsers()) {
            userPermissionService.removePermissions(username, groupEntity.getPermission_groups().stream().map(PermissionGroupEntity::getPermission).toList());
        }
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserPermissionService(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }

    private void initPermission() {
        List<String> permissions = groupRepository.findAll().stream().map(GroupEntity::getName).map(String::toLowerCase).map(groupName -> "group." + groupName).toList();
        userPermissionService.getSpecialPermissions().addAll(permissions);
    }
}
