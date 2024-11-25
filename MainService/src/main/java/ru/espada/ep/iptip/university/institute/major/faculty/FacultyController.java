package ru.espada.ep.iptip.university.institute.major.faculty;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/faculty")
public class FacultyController {

    private FacultyService facultyService;

    @PostMapping("/faculty")
    public ResponseEntity<?> createFaculty(@Valid @RequestBody CreateFacultyModel createFacultyModel) {
        Long id = facultyService.createFaculty(createFacultyModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Autowired
    public void setFacultyService(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

}

