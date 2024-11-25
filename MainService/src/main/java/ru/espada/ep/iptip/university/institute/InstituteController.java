package ru.espada.ep.iptip.university.institute;

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
@RequestMapping("/institute")
public class InstituteController {

    private InstituteService instituteService;

    @PostMapping("/institute")
    @PreAuthorize("hasPermission(#createInstituteModel, 'university.{universityId}')")
    public ResponseEntity<?> createInstitute(@Valid @RequestBody CreateInstituteModel createInstituteModel) {
        Long id = instituteService.createInstitute(createInstituteModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Autowired
    public void setInstituteService(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

}

