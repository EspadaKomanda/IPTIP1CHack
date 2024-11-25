package ru.espada.ep.iptip.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.espada.ep.iptip.event.models.requests.CreateEventForStudyGroupsRequest;
import ru.espada.ep.iptip.event.models.requests.CreateEventRequest;
import ru.espada.ep.iptip.event.models.requests.ModifyEventRequest;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;
import ru.espada.ep.iptip.study_groups.StudyGroupRepository;
import ru.espada.ep.iptip.study_groups.study_group_event.StudyGroupEventEntity;
import ru.espada.ep.iptip.study_groups.study_group_event.StudyGroupEventRepository;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    // TODO: implementation

    @Override
    public EventEntity modifyEvent(Principal principal, ModifyEventRequest request) {
        //TODO: check permission
        EventEntity eventEntity = eventRepository.findById(request.getId()).orElseThrow();

        eventEntity.setName(Optional.ofNullable(request.getName()).orElse(eventEntity.getName()));
        eventEntity.setDate(Optional.ofNullable(request.getDate()).orElse(eventEntity.getDate()));
        // FIXME: i am not sure, can bools be null in java? what will happen down here?
        eventEntity.setWeekday(Optional.ofNullable(request.getWeekday()).orElse(eventEntity.getWeekday()));
        eventEntity.set_week_event(Optional.ofNullable(request.is_week_event()).orElse(eventEntity.is_week_event()));
        eventEntity.setBegin_date(Optional.ofNullable(request.getBegin_date()).orElse(eventEntity.getBegin_date()));
        eventEntity.setEnd_date(Optional.ofNullable(request.getEnd_date()).orElse(eventEntity.getEnd_date()));
        eventEntity.setDuration(Optional.ofNullable(request.getDuration()).orElse(eventEntity.getDuration()));

        return eventRepository.save(eventEntity);
    }

    @Override
    public EventEntity getEvent(Principal principal, Long id) {
        //TODO: check permission
        return null;
    }

    @Override
    public List<StudyGroupEntity> eventStudyGroups(Principal principal, Long id) {
        //TODO: check permission
        return List.of();
    }

    @Override
    public void deleteEvent(Principal principal, Long id) {
        //TODO: check permission

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
