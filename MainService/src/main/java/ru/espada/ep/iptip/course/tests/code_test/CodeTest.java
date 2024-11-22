package ru.espada.ep.iptip.course.tests.code_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import ru.espada.ep.iptip.code_starter.impl.JavaCodeStarter;
import ru.espada.ep.iptip.course.tests.question.QuestionType;
import ru.espada.ep.iptip.course.tests.answer.AnswerEntity;
import ru.espada.ep.iptip.user.UserEntity;

import java.io.File;
import java.io.IOException;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CodeTest {

    private UserEntity user;
    private AnswerEntity answer;
    private File user_dir;
    @Autowired
    private ObjectMapper objectMapper;
    private String operationUUID;

    public void test(UserEntity user, AnswerEntity answer, String operationUUID) {
        this.user = user;
        this.answer = answer;
        this.operationUUID = operationUUID;

        user_dir = new File("app/users/" + user.getUsername() + "/" + operationUUID);
        if (!user_dir.exists()) {
            user_dir.mkdirs();
        } else {
            user_dir.delete();
            user_dir.mkdirs();
        }

        startTesting();
        deleteDirectory(user_dir);
    }

    private void startTesting() {
        try {
            JsonNode jsonNode = objectMapper.readTree(answer.getQuestion().getContent());
            for (int i = 0; i < jsonNode.size() / 2; i++) {
                String input = jsonNode.get("test_" + i).asText();
                String answer = jsonNode.get("answer_" + i).asText();
                if (startTest(input, answer)) {
                    System.out.println("Test " + i + " passed");
                } else {
                    System.out.println("Test " + i + " failed. " + input + " != " + answer);
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean startTest(String input, String answer) {
        File file = new File(user_dir, "input.txt");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            java.nio.file.Files.write(file.toPath(), input.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = test();
        if (result.endsWith("\n")) {
            result = result.substring(0, result.length() - 1);
        }
        if (result.equals(answer)) {
            return true;
        }
        return false;
    }

    private String test() {
        String result = "error";

        try {
            JsonNode jsonNode = objectMapper.readTree(answer.getAnswer());
            String lang = jsonNode.get("lang").asText();
            switch (answer.getQuestion().getQuestionType()) {
                case QuestionType.CODE:
                    switch (lang) {
                        case "java":
                            result = testJavaCode(jsonNode.get("payload").asText());
                            break;
                        case "python":
                            break;
                        default:
                    }
                    break;
                case QuestionType.FILE:
                    result = testJavaClass(new File("app/users/" + user.getUsername() + "/" + operationUUID));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String testJavaCode(String payload) {
        File file = new File(user_dir, "Main.java");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(file)) {
            fileWriter.write(payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return testJavaClass(file.getParentFile());
    }

    @SneakyThrows
    private String testJavaClass(File file) {
        JavaCodeStarter codeStarter = new JavaCodeStarter(file.getPath());
        codeStarter.setDocker(true);
        codeStarter.start();
        String result = codeStarter.getOutput();
        codeStarter.stop();

        return result;
    }

    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file); // Рекурсивно удаляем содержимое
                }
            }
        }
        directory.delete(); // Удаляем саму папку
    }

}
