package ru.espada.ep.iptip.study_groups.study_group_event;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudyGroupEventServiceImpl implements StudyGroupEventService {
    private StudyGroupEventRepository studyGroupEventRepository;

    @Autowired
    public void setStudyGroupEventRepository(StudyGroupEventRepository studyGroupEventRepository) {
        this.studyGroupEventRepository = studyGroupEventRepository;
    }
}
