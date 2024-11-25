package ru.espada.ep.iptip.event;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}

