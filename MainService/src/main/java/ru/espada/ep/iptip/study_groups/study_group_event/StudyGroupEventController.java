package ru.espada.ep.iptip.study_groups.study_group_event;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/studyGroupEvent")
public class StudyGroupEventController {

}

