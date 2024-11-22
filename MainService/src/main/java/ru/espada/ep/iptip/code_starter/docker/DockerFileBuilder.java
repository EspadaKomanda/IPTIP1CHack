package ru.espada.ep.iptip.code_starter.docker;

public class DockerFileBuilder {

    private String language;
    private String languageVersion;
    private String projectType;
    private String projectName;
    private String buildSystem;

    // строки
    private String commandLine;
    private String imageName;
    private String buildLine;
    /** Используется если особого механизма сборки не поддерживается */
    private String finalLine;


    public DockerFileBuilder setLanguage(String language, String languageVersion) {
        this.language = language;
        this.languageVersion = languageVersion;

        switch (language) {
            case "java":
                switch (languageVersion) {
                    case "8":
                        this.imageName = "openjdk:8";
                        break;
                    case "11":
                        this.imageName = "openjdk:11";
                        break;
                    case "17":
                        this.imageName = "openjdk:17";
                        break;
                    case "21":
                        this.imageName = "openjdk:21";
                        break;
                }
                break;
            case "python":
                this.imageName = "python:" + languageVersion;
                break;
        }

        return this;
    }

    public DockerFileBuilder setProjectType(String projectType) {
        this.projectType = projectType;

        switch (language) {
            case "java":
                switch (projectType) {
                    case "maven", "gradle", "jar":
                        this.commandLine = "\"java\",\"-jar\",\"/app/app.jar\"";
                        break;
                    case "class":
                        StringBuilder sb = new StringBuilder();
                        sb.append("FROM ").append(imageName).append(" as builder").append("\n");
                        sb.append("WORKDIR /app\n");
                        sb.append("COPY . /app\n");
                        sb.append("RUN javac Main.java\n");

                        sb.append("FROM ").append(imageName).append(" as final").append("\n");
                        sb.append("WORKDIR /app\n");
                        sb.append("COPY --from=builder /app/Main.class /app/\n");
                        sb.append("COPY input.txt /app\n");
                        sb.append("CMD [\"java\", \"Main\"]");
                        this.finalLine = sb.toString();

                        break;
                }
                break;
            case "python":
                this.commandLine = "python app.py";
                break;
        }

        return this;
    }

    public DockerFileBuilder setBuildSystem(String buildSystem) {
        this.buildSystem = buildSystem;
        StringBuilder sb = new StringBuilder();

        switch (language) {
            case "java":
                switch (buildSystem) {
                    case "maven":
                        sb.append("FROM maven:latest AS stage1\n");
                        break;
                    case "gradle":
                        sb.append("FROM gradle:latest\n");
                        break;
                }
                break;
        }

        sb.append("WORKDIR /app\n");

        switch (language) {
            case "java":
                switch (buildSystem) {
                    case "maven":
                        sb.append("COPY pom.xml /app\n");
                        sb.append("RUN mvn dependency:resolve\n");
                        break;
                    case "gradle":
                        sb.append("COPY build.gradle /app\n");
                        break;
                }
                break;
        }

        sb.append("COPY . /app\n");

        switch (language) {
            case "java":
                switch (buildSystem) {
                    case "maven":
                        sb.append("RUN mvn clean\n");
                        sb.append("RUN mvn package -DskipTests\n");
                        break;
                    case "gradle":
                        sb.append("RUN gradle build\n");
                        break;
                }
                break;
        }

        this.buildLine = sb.toString();
        return this;
    }

    public DockerFileBuilder setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String build() {
        if (finalLine != null) {
            return finalLine;
        }

        StringBuilder sb = new StringBuilder();
        if (buildLine == null) {
            throw new RuntimeException("buildLine is null");
        }
        sb.append(buildLine);

        if (imageName == null) {
            throw new RuntimeException("imageName is null");
        }
        sb.append("FROM ").append(imageName).append("\n");

        sb.append("COPY --from=stage1 /app/target/*.jar /app/app.jar").append("\n");
        sb.append("COPY . /app").append("\n");
        if (commandLine == null) {
            throw new RuntimeException("commandLine is null");
        }
        sb.append("CMD ").append("[").append(commandLine).append("]").append("\n");
        return sb.toString();
    }

}
