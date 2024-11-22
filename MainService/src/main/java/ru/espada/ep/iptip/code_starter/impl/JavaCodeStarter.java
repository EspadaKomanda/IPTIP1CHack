package ru.espada.ep.iptip.code_starter.impl;

import lombok.Getter;
import lombok.Setter;
import ru.espada.ep.iptip.code_starter.CodeStarter;
import ru.espada.ep.iptip.code_starter.docker.DockerFileBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class JavaCodeStarter implements CodeStarter {

    @Getter
    private Process process;
    private String path;
    private String[] args;
    private String output;
    @Setter
    private boolean docker = false;
    @Getter
    private JavaProjectType projectType;
    @Getter
    private ProcessState state = ProcessState.OFF;
    private File startFile;

    private enum ProcessState {
        OFF,
        STARTED,
        COMPILING,
        RUNNING,
        FINISHED
    }

    private enum JavaProjectType {
        JAR,
        MAVEN,
        GRADLE,
        CLASS
    }

    public JavaCodeStarter(String path, String... args) {
        this.path = path;
        this.args = args;

        checkProjectType();
    }

    @Override
    public void start() {
        if (docker) {
            setState(ProcessState.STARTED);
            startDocker();
            return;
        }
        try {
            setState(ProcessState.STARTED);

            switch (projectType) {
                case CLASS -> {
                    startClass();
                }
            }

            process.waitFor();
            setState(ProcessState.FINISHED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (process != null) {
            process.destroy();
        }
    }

    @Override
    public String getOutput() throws InterruptedException {
        if (output != null) {
            return output;
        }
        process.waitFor();
        StringBuilder sb = new StringBuilder();

        // Чтение стандартного вывода
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Чтение стандартного потока ошибок
        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = errorReader.readLine()) != null) {
                sb.append("ERROR: ").append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        output = sb.toString();
        return sb.toString();
    }

    private void startDocker() {
        try {
            String appName = UUID.randomUUID().toString();
            String containerName = UUID.randomUUID().toString();
            createDockerFile(appName);

            Process docker_build = new ProcessBuilder("docker", "build", "-t", appName, path)
                    .start();
            setState(ProcessState.COMPILING);
            docker_build.waitFor();
            process = new ProcessBuilder("docker", "run", "--name", containerName, appName)
                    .start();
            setState(ProcessState.RUNNING);
            process.waitFor();
            StringBuilder sb = new StringBuilder();

            // Чтение стандартного вывода
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            output = sb.toString();
            setState(ProcessState.FINISHED);

            File dockerFile = new File(path + "/Dockerfile");
            dockerFile.delete();

            // Удаление контейнера
            Process docker_rm = new ProcessBuilder("docker", "rm", "-f", containerName)
                    .start();
            docker_rm.waitFor();

            // Удаление образа
            Process docker_rmi = new ProcessBuilder("docker", "rmi", "-f", appName)
                    .start();
            docker_rmi.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDockerFile(String appName) throws IOException {
        File dockerFile = new File(path + "/Dockerfile");
        if (dockerFile.exists()) {
            throw new RuntimeException("Dockerfile already exists");
        }
        if (!dockerFile.createNewFile()) {
            throw new RuntimeException("Failed to create Dockerfile");
        }
        DockerFileBuilder dockerFileBuilder = new DockerFileBuilder();
        String dockerFileContent = dockerFileBuilder
                .setProjectName(appName)
                .setLanguage("java", "21")
                .setProjectType(getProjectType().toString().toLowerCase())
                .setBuildSystem(getProjectType().toString().toLowerCase())
                .build();

        try (java.io.FileWriter fileWriter = new java.io.FileWriter(dockerFile)) {
            fileWriter.write(dockerFileContent);
        }
    }

    private void startClass() throws InterruptedException, IOException {
        // Компиляция Java-файла
        Process compileProcess = new ProcessBuilder("javac", path + "/" + startFile.getName()).start();
        setState(ProcessState.COMPILING);
        compileProcess.waitFor();

        // Запуск скомпилированного Java-класса
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", path, startFile.getName().replace(".class", ""));
        process = processBuilder.start();

        setState(ProcessState.RUNNING);
    }

    private void startMaven() throws IOException, InterruptedException {
        // Запуск Maven
        Process mavenProcess = new ProcessBuilder("mvn", "compile")
                .directory(new File(path))
                .start();
        setState(ProcessState.COMPILING);
        mavenProcess.waitFor();

        // Запуск скомпилированного Maven-класса
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", path + "/target/" + startFile.getName());
        process = processBuilder.start();

        setState(ProcessState.RUNNING);
    }

    private void setState(ProcessState state) {
        this.state = state;
    }

    private void checkProjectType() {
        for (File file : Objects.requireNonNull(new File(path).listFiles())) {
            if (file.getName().endsWith(".jar")) {
                projectType = JavaProjectType.JAR;
                startFile = file;
                return;
            }
            if (file.getName().equals("pom.xml")) {
                projectType = JavaProjectType.MAVEN;
                startFile = file;
                return;
            }
            if (file.getName().equals("build.gradle")) {
                projectType = JavaProjectType.GRADLE;
                startFile = file;
                return;
            }
            if (file.getName().endsWith(".java")) {
                projectType = JavaProjectType.CLASS;
                startFile = file;
                return;
            }
        }
    }
}
