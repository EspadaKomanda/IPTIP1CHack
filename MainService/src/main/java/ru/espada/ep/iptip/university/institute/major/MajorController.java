package ru.espada.ep.iptip.university.institute.major;

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
@RequestMapping("/major")
public class MajorController {

    private MajorService majorService;

    @PostMapping("/major")
    public ResponseEntity<?> createMajor(@Valid @RequestBody CreateMajorModel createMajorModel) {
        Long id = majorService.createMajor(createMajorModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Autowired
    public void setMajorService(MajorService majorService) {
        this.majorService = majorService;
    }

}

