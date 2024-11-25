package ru.espada.ep.iptip.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.espada.ep.iptip.event.models.requests.CreateEventForStudyGroupsRequest;
import ru.espada.ep.iptip.event.models.requests.CreateEventRequest;
import ru.espada.ep.iptip.study_groups.StudyGroupRepository;
import ru.espada.ep.iptip.study_groups.study_group_event.StudyGroupEventEntity;
import ru.espada.ep.iptip.study_groups.study_group_event.StudyGroupEventRepository;

import java.security.Principal;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService{
    private EventRepository eventRepository;
    private StudyGroupEventEntity studyGroupEventEntity;
    private StudyGroupRepository studyGroupRepository;

    private final StudyGroupEventRepository studyGroupEventRepository;

    @Override
    public EventEntity createEvent(Principal principal, CreateEventRequest request) {
        //TODO: check permission

        EventEntity eventEntity = EventEntity.builder()
                .name(request.getName())
                .date(request.getDate())
                .weekday(request.getWeekday())
                .is_week_event(request.is_week_event())
                .begin_date(request.getBegin_date())
                .end_date(request.getEnd_date())
                .duration(request.getDuration())
                .build();
        return eventRepository.save(eventEntity);
    }

    @Override
    @Transactional
    public EventEntity createEventForStudyGroups(Principal principal, CreateEventForStudyGroupsRequest request) {

        EventEntity eventEntity = EventEntity.builder()
                .name(request.getName())
                .date(request.getDate())
                .weekday(request.getWeekday())
                .is_week_event(request.is_week_event())
                .begin_date(request.getBegin_date())
                .end_date(request.getEnd_date())
                .duration(request.getDuration())
                .build();
        eventEntity = eventRepository.save(eventEntity);

        for (Long studyGroupId : request.getStudy_group_ids()) {
            StudyGroupEventEntity studyGroupEventEntity = StudyGroupEventEntity.builder()
                    .eventId(eventEntity.getId())
                    .studyGroupId(studyGroupId)
                    .build();
            studyGroupEventEntity = studyGroupEventRepository.save(studyGroupEventEntity);
        }
        return eventEntity;
    }

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setStudyGroupRepository(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    @Autowired
    public void setStudyGroupEventEntity(StudyGroupEventEntity studyGroupEventEntity) {
        this.studyGroupEventEntity = studyGroupEventEntity;
    }
}
