package ru.espada.ep.iptip.user.permission.groups;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.UserService;
import ru.espada.ep.iptip.user.permission.UserPermissionService;

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
    public List<GroupEntity> getGroups() {
        return groupRepository.findAll();
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
        List<String> permissions = getGroups().stream().map(GroupEntity::getName).map(String::toLowerCase).map(groupName -> "group." + groupName).toList();
        userPermissionService.getSpecialPermissions().addAll(permissions);
    }
}
