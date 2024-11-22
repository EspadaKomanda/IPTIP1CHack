package ru.espada.ep.iptip.course.tests;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.course.tests.model.CreateTestModel;
import ru.espada.ep.iptip.course.tests.question.model.CreateQuestionModel;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/test")
public class TestController {

    private TestService testService;

    @PostMapping("/create")
    public ResponseEntity<?> createTest(@Valid @RequestBody CreateTestModel createTestModel) {
        testService.createTest(createTestModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/create/{testId}/question")
    public ResponseEntity<?> createQuestion(@PathVariable Long testId, @Valid @RequestBody CreateQuestionModel createQuestionModel) {
        testService.createQuestion(createQuestionModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Autowired
    public void setTestService(TestService testService) {
        this.testService = testService;
    }
}
