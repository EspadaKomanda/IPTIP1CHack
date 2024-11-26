package ru.espada.ep.iptip.study_groups;

public interface StudyGroupService {
    boolean hasPermission(String name, Long studyGroupId);
}
