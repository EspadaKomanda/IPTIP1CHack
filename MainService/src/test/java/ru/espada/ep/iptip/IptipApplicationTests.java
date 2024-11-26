package ru.espada.ep.iptip;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.espada.ep.iptip.code_starter.impl.JavaCodeStarter;
import ru.espada.ep.iptip.git.GitHubUtil;
import ru.espada.ep.iptip.user.permission.UserPermissionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootTest
class IptipApplicationTests {

    @Autowired
    private GitHubUtil gitHubUtil;
    @Autowired
    private UserPermissionService userPermissionService;

    @Test
    void contextLoads() {
    }

    @Test
    void gitHubUtilTest() {
        String fileURL = gitHubUtil.getFilePathFromUrl("https://github.com/maxdo1511/FlatterTest/tree/minecraft_shop_add_remove");
        gitHubUtil.downloadFile(fileURL, "app/git/data.zip");
    }

    @Test
    void runJavaCodeStarter() throws InterruptedException {
        JavaCodeStarter codeStarter = new JavaCodeStarter("app/local/test_project");
        codeStarter.setDocker(true);
        new Thread(() -> {
            while (true) {
                System.out.println(codeStarter.getState());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }
        }).start();
        codeStarter.start();
        System.out.println(codeStarter.getOutput());
        codeStarter.stop();
    }

    @Test
    void testDocker() throws IOException, InterruptedException {
        Process docker_build = new ProcessBuilder("docker", "build", "-t", "myapp", "app/docker")
                .start();
        docker_build.waitFor();
        Process docker_run = new ProcessBuilder("docker", "run", "myapp")
                .start();
        docker_run.waitFor();
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(docker_run.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(sb);
    }

    @Test
    void testPermissions() {
        System.out.println(String.join("\n", userPermissionService.getPermissions()));
    }

    /*
    @Test
    void testCodeTest() {
        String uuid = UUID.randomUUID().toString();

        File file = new File("app/users/" + "admin" + "/" + uuid);
        file.mkdirs();

        // copy file
        try {
            java.nio.file.Files.copy(new File("app/local/test_class/Main.java").toPath(), new File("app/users/" + "admin" + "/" + uuid + "/Main.java").toPath());
        } catch (IOException e) {
        }

        CodeTest codeTest = applicationContext.getBean(CodeTest.class);
        codeTest.test(
                new UserEntity(
                        0L,
                        "admin",
                        null,
                        null,
                        null,
                        null,
                        new HashSet<>(),
                        new HashSet<>()
                ),
                new AnswerEntity(
                        0L,
                        new QuestionEntity(
                                0L,
                                0,
                                "test",
                                QuestionType.FILE,
                                "{\n" +
                                        "  \"test_0\": \"Hello World!\",\n" +
                                        "  \"answer_0\": \"Hello World!\",\n" +
                                        "  \"test_1\": \"Hello World!\",\n" +
                                        "  \"answer_1\": \"Hello World\"\n" +
                                        "}"
                        ),
                        "{\n" +
                                "  \"lang\": \"java\"\n" +
                                "}"
                ),
                uuid
        );
    }

     */

}
