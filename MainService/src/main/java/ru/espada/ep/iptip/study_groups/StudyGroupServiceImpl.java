package ru.espada.ep.iptip.study_groups;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.espada.ep.iptip.exceptions.custom.ForbiddenException;
import ru.espada.ep.iptip.study_groups.models.requests.*;
import ru.espada.ep.iptip.university.UniversityEntity;
import ru.espada.ep.iptip.university.institute.InstituteEntity;
import ru.espada.ep.iptip.university.institute.major.MajorEntity;
import ru.espada.ep.iptip.university.institute.major.faculty.FacultyEntity;
import ru.espada.ep.iptip.university.institute.major.faculty.FacultyRepository;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.UserService;
import ru.espada.ep.iptip.user.permission.UserPermissionEntity;
import ru.espada.ep.iptip.user.permission.UserPermissionService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyGroupServiceImpl implements StudyGroupService {

    private StudyGroupRepository studyGroupRepository;
    private UserService userService;
    private UserPermissionService userPermissionService;
    private FacultyRepository facultyRepository;

    @Override
    public boolean hasPermission(String username, Long studyGroupId) {
        UserEntity user = userService.getUser(username);
        if (user == null) {
            return false;
        }
        List<String> permissions = user.getPermissions().stream().map(UserPermissionEntity::getName).toList();
        if (permissions.contains("admin")) return true;
        StudyGroupEntity studyGroupEntity = studyGroupRepository.findById(studyGroupId).orElse(null);
        if (studyGroupEntity == null) {
            return false;
        }
        FacultyEntity faculty = studyGroupEntity.getFaculty();
        if (faculty == null) {
            return false;
        }
        MajorEntity major = faculty.getMajor();
        if (major == null) {
            return false;
        }
        InstituteEntity institute = major.getInstitute();
        if (institute == null) {
            return false;
        }
        UniversityEntity university = institute.getUniversity();
        if (university == null) {
            return false;
        }
        return userPermissionService.hasPermission(username, "university.%s.institute.%s.major.%s.faculty.%s.study_group.%s".formatted(
                university.getId(), institute.getId(), major.getId(), faculty.getId(), studyGroupEntity.getId()
        ));
    }

    @Override
    public StudyGroupEntity createStudyGroup(Principal principal, CreateStudyGroupRequest request) {
        FacultyEntity faculty = facultyRepository.findById(request.getFacultyId()).orElseThrow(() -> new IllegalArgumentException("Invalid faculty id"));
        if (!userPermissionService.hasPermission(principal.getName(), "university.%s.institute.%s.major.%s.faculty.%s".formatted(
                faculty.getMajor().getInstitute().getUniversity().getId(),
                faculty.getMajor().getInstitute().getId(),
                faculty.getMajor().getId(),
                faculty.getId()
        ))) throw new ForbiddenException("Permission denied");

        StudyGroupEntity studyGroup = StudyGroupEntity.builder()
                .name(request.getName())
                .faculty(faculty)
                .build();

        return studyGroupRepository.save(studyGroup);
    }

    @Override
    @Transactional
    public StudyGroupEntity createStudyGroupWithUsers(Principal principal, CreateStudyGroupWithUsersRequest createRequest) {
        StudyGroupEntity studyGroup = createStudyGroup(principal, CreateStudyGroupRequest.builder()
                .facultyId(createRequest.getFacultyId())
                .name(createRequest.getName())
                .build());

        attachUserToStudyGroup(principal, AttachUsersToStudyGroupRequest.builder()
                .studyGroupId(studyGroup.getId())
                .userIds(createRequest.getUsers())
                .build()
        );

        return studyGroup;
    }

    @Override
    public StudyGroupEntity modifyStudyGroup(Principal principal, ModifyStudyGroupRequest request) {
        // TODO: implementation
        return null;
    }

    @Override
    public StudyGroupEntity getStudyGroup(Principal principal, Long studyGroupId) {
        return studyGroupRepository.findById(studyGroupId).orElse(null);
    }

    @Override
    public void deleteStudyGroup(Principal principal, Long studyGroupId) {
        studyGroupRepository.deleteById(studyGroupId);
    }

    @Override
    @Transactional
    public void attachUserToStudyGroup(Principal principal, AttachUsersToStudyGroupRequest request) {
        StudyGroupEntity studyGroup = studyGroupRepository.findById(request.getStudyGroupId()).orElseThrow(() -> new IllegalArgumentException("Invalid study group id"));
        for (Long userId : request.getUserIds()) {
            studyGroup.getUsers().add(userService.getUser(userId));
        }
        studyGroupRepository.save(studyGroup);
    }

    @Override
    public void detachUserFromStudyGroup(Principal principal, DetachUserFromStudyGroupRequest request) {
        // TODO: implementation

    }

    @Override
    public void setStudyGroupMembersSemester(Principal principal, SetStudyGroupMembersSemesterRequest request) {
        // TODO: implementation
    }

    @Override
    public List<Long> getStudyGroupMembers(Principal principal, Long studyGroupId) {
        // TODO: implementation
        return List.of();
    }

    @Autowired
    public void setStudyGroupRepository(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserPermissionService(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }

    @Autowired
    public void setFacultyRepository(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
}
