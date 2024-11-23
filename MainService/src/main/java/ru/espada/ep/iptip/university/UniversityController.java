package ru.espada.ep.iptip.university;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/university")
public class UniversityController {
    private UniversityService universityService;

    @Autowired
    public void setUniversityService(UniversityService universityService) {
        this.universityService = universityService;
    }
}

