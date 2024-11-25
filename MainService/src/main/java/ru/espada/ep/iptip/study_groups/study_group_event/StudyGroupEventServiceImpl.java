package ru.espada.ep.iptip.study_groups.study_group_event;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.event.EventEntity;
import ru.espada.ep.iptip.event.EventRepository;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;
import ru.espada.ep.iptip.study_groups.StudyGroupRepository;
import ru.espada.ep.iptip.study_groups.study_group_event.model.requests.AttachEventToStudyGroupRequest;
import ru.espada.ep.iptip.study_groups.study_group_event.model.requests.DetachEventFromStudyGroupRequest;

import java.security.Principal;

@RequiredArgsConstructor
@Service
public class StudyGroupEventServiceImpl implements StudyGroupEventService {
    private StudyGroupEventRepository studyGroupEventRepository;
    private EventRepository eventRepository;
    private StudyGroupRepository studyGroupRepository;

    // TODO: deal with the principal stuff here
    @Override
    public StudyGroupEventEntity attachEventToStudyGroup(Principal principal, AttachEventToStudyGroupRequest request) {

        EventEntity event = eventRepository.findById(request.getEventId()).orElseThrow();
        StudyGroupEntity studyGroup = studyGroupRepository.findById(request.getStudyGroupId()).orElseThrow();

        return studyGroupEventRepository.save(StudyGroupEventEntity.builder()
                .eventId(event.getId())
                .studyGroupId(studyGroup.getId())
                .build());
    }

    // TODO: deal with the principal stuff here as well
    @Override
    public void detachEventFromStudyGroup(Principal principal, DetachEventFromStudyGroupRequest request) {

        EventEntity event = eventRepository.findById(request.getEventId()).orElseThrow();
        StudyGroupEntity studyGroup = studyGroupRepository.findById(request.getStudyGroupId()).orElseThrow();

        StudyGroupEventEntity studyGroupEvent = studyGroupEventRepository
                .findByEventIdAndStudyGroupId(event.getId(), studyGroup.getId());

        studyGroupEventRepository.delete(studyGroupEvent);
    }

    @Autowired
    public void setStudyGroupEventRepository(StudyGroupEventRepository studyGroupEventRepository) {
        this.studyGroupEventRepository = studyGroupEventRepository;
    }

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setStudyGroupRepository(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }
}
