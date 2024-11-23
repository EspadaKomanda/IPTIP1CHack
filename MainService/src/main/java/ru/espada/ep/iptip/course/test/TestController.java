package ru.espada.ep.iptip.course.test;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.course.test.model.CreateTestModel;
import ru.espada.ep.iptip.course.test.question.model.CreateQuestionModel;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/test")
public class TestController {

    private TestService testService;

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
