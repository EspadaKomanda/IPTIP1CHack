package ru.espada.ep.iptip.university;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/university")
public class UniversityController {
    private UniversityService universityService;

    @PostMapping("/university")
    @PreAuthorize("hasPermission(#createUniversityModel, 'university')")
    public ResponseEntity<Long> createUniversity(@Valid @RequestBody CreateUniversityModel createUniversityModel) {
        Long id = universityService.createUniversity(createUniversityModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Autowired
    public void setUniversityService(UniversityService universityService) {
        this.universityService = universityService;
    }
}

