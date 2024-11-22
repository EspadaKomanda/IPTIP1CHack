package ru.espada.ep.iptip.course.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.course.CourseEntity;
import ru.espada.ep.iptip.course.tests.model.CreateTestModel;
import ru.espada.ep.iptip.course.tests.question.QuestionEntity;
import ru.espada.ep.iptip.course.tests.question.QuestionRepository;
import ru.espada.ep.iptip.course.tests.question.QuestionType;
import ru.espada.ep.iptip.course.tests.question.model.CreateQuestionModel;
import ru.espada.ep.iptip.user.UserService;

@Service
public class TestService {

    private TestRepository testRepository;
    private QuestionRepository questionRepository;
    private UserService userService;


    @Autowired
    public void setTestRepository(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public void createTest(CreateTestModel createTestModel) {
        TestEntity testEntity = TestEntity.builder()
                .name(createTestModel.getName())
                .description(createTestModel.getDescription())
                .position(createTestModel.getPosition())
                .course(CourseEntity.builder().id(createTestModel.getCourseId()).build())
                .build();
        testRepository.save(testEntity);
    }

    public void createQuestion(CreateQuestionModel createQuestionModel) {
        QuestionEntity questionEntity = QuestionEntity.builder()
                .title(createQuestionModel.getTitle())
                .position(createQuestionModel.getPosition())
                .content(createQuestionModel.getContent())
                .questionType(QuestionType.valueOf(createQuestionModel.getQuestionType()))
                .build();
        questionRepository.save(questionEntity);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
}
