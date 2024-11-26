package ru.espada.ep.iptip.study_groups;

import ru.espada.ep.iptip.study_groups.models.requests.*;

import java.security.Principal;
import java.util.List;

public interface StudyGroupService {
    boolean hasPermission(String name, Long studyGroupId);

    StudyGroupEntity createStudyGroup(Principal principal, CreateStudyGroupRequest request);
    StudyGroupEntity createStudyGroupWithUsers(Principal principal, CreateStudyGroupWithUsersRequest createRequest);
    StudyGroupEntity modifyStudyGroup(Principal principal, ModifyStudyGroupRequest request);
    StudyGroupEntity getStudyGroup(Principal principal, Long studyGroupId);
    void deleteStudyGroup(Principal principal, Long studyGroupId);

    void attachUserToStudyGroup(Principal principal, AttachUserToStudyGroupRequest request);
    void detachUserFromStudyGroup(Principal principal, DetachUserFromStudyGroupRequest request);
    void setStudyGroupMembersSemester(Principal principal, SetStudyGroupMembersSemesterRequest request);
    List<Long> getStudyGroupMembers(Principal principal, Long studyGroupId);
}
