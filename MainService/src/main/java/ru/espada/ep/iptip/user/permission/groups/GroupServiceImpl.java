package ru.espada.ep.iptip.user.permission.groups;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.espada.ep.iptip.user.UserService;
import ru.espada.ep.iptip.user.permission.UserPermissionService;
import ru.espada.ep.iptip.user.permission.groups.models.AddPermissionsToGroup;
import ru.espada.ep.iptip.user.permission.groups.models.UserPermissionForGroupReqest;
import ru.espada.ep.iptip.user.permission.groups.models.CreateGroupModel;
import ru.espada.ep.iptip.user.permission.groups.models.GroupToUserRequest;
import ru.espada.ep.iptip.user.permission.groups.permission_group.PermissionGroupEntity;
import ru.espada.ep.iptip.user.permission.groups.permission_group.PermissionGroupEntityRepository;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private UserService userService;
    private UserPermissionService userPermissionService;

    private final PermissionGroupEntityRepository permissionGroupEntityRepository;

    @PostConstruct
    public void init() {
        initPermission();
    }

    public void addGroup(GroupEntity groupEntity) {
        groupRepository.save(groupEntity);
        initPermission();
    }

    @Override
    @Transactional
    public List<GroupEntityDto> getGroups(String username) {
        return groupRepository.findAll().stream()
                .filter(groupEntity -> userPermissionService.hasPermission(username, "group." + groupEntity.getName()))
                .map(groupEntity -> GroupEntityDto.builder()
                        .id(groupEntity.getId())
                        .name(groupEntity.getName())
                        .color(groupEntity.getColor())
                        .groupPermissions(groupEntity.getPermissionGroups().stream().map(PermissionGroupEntity::getPermission).toList())
                        .build())
                .toList();
    }

    @Override
    public GroupEntityDto getGroup(Long id) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow();
        return GroupEntityDto.builder()
                .id(groupEntity.getId())
                .name(groupEntity.getName())
                .color(groupEntity.getColor())
                .groupPermissions(groupEntity.getPermissionGroups().stream().map(PermissionGroupEntity::getPermission).toList())
                .build();
    }

    @Override
    @Transactional
    public void createGroup(Principal principal, CreateGroupModel createGroupModel) {
        for (String permission : createGroupModel.getPermissions()) {
            if (!userPermissionService.hasPermission(principal.getName(), permission)) {
                throw new RuntimeException("User " + principal.getName() + " doesn't have permission " + permission);
            }
        }

        GroupEntity groupEntity = GroupEntity.builder()
                .name(createGroupModel.getName())
                .color(createGroupModel.getColor())
                .build();
        addGroup(groupEntity);

        groupRepository.save(groupEntity);

        for (String permission : createGroupModel.getPermissions()) {
            PermissionGroupEntity permissionGroupEntity = PermissionGroupEntity.builder()
                    .groupId(groupEntity.getId())
                    .permission(permission)
                    .build();
            permissionGroupEntityRepository.save(permissionGroupEntity);
        }

        for (String username : createGroupModel.getUsersCanManage()) {
            userPermissionService.addPermission(username, "group." + createGroupModel.getName(), -1L, -1L);
        }
    }

    @Override
    public void deleteGroup(String group) {
        groupRepository.deleteGroupEntityByName(group);
    }

    @Override
    public void addUserPermission(UserPermissionForGroupReqest userPermissionForGroupReqest) {
        userPermissionService.addPermission(userPermissionForGroupReqest.getUsername(), "group." + userPermissionForGroupReqest.getGroup(), -1L, -1L);
    }

    @Override
    public void addGroupPermission(Principal principal, AddPermissionsToGroup addPermissionsToGroup) {
        for (String permission : addPermissionsToGroup.getPermissions()) {
            if (!userPermissionService.hasPermission(principal.getName(), permission)) {
                throw new RuntimeException("User " + principal.getName() + " doesn't have permission " + permission);
            }
        }

        GroupEntity groupEntity = groupRepository.findGroupEntityByName(addPermissionsToGroup.getGroup()).orElseThrow();
        for (String permission : addPermissionsToGroup.getPermissions()) {
            PermissionGroupEntity permissionGroupEntity = PermissionGroupEntity.builder()
                    .groupId(groupEntity.getId())
                    .permission(permission)
                    .build();
            permissionGroupEntityRepository.save(permissionGroupEntity);
        }
    }

    @Override
    public void giveGroupToUsers(GroupToUserRequest groupToUserRequest) {
        GroupEntity groupEntity = groupRepository.findGroupEntityByName(groupToUserRequest.getGroup()).orElseThrow();

        for (String username : groupToUserRequest.getUsers()) {
            userPermissionService.addPermissions(username, groupEntity.getPermissionGroups().stream().map(PermissionGroupEntity::getPermission).toList(), -1L, -1L);
        }
    }

    @Override
    public void removeGroupFromUser(GroupToUserRequest groupToUserRequest) {
        GroupEntity groupEntity = groupRepository.findGroupEntityByName(groupToUserRequest.getGroup()).orElseThrow();

        for (String username : groupToUserRequest.getUsers()) {
            userPermissionService.removePermissions(username, groupEntity.getPermissionGroups().stream().map(PermissionGroupEntity::getPermission).toList());
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
