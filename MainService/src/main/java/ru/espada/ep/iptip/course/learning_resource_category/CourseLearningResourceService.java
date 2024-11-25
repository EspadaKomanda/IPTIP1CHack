package ru.espada.ep.iptip.course.learning_resource_category;

import ru.espada.ep.iptip.course.learning_resource_category.model.CourseLearningResourceEntityDto;
import ru.espada.ep.iptip.course.learning_resource_category.model.CreateCourseLEarningFolderModel;
import ru.espada.ep.iptip.course.learning_resource_category.model.CreateCourseLearningResourceModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CourseLearningResourceService {

    List<CourseLearningResourceEntityDto> getCourseLearningResource(Long categoryId);

    Long createResource(CreateCourseLearningResourceModel createCourseLearningResourceModel);

    void deleteResource(Long id);

    Long createFolder(CreateCourseLEarningFolderModel createCourseLEarningFolderModel);

    CompletableFuture<String> uploadFile(byte[] avatar, String contentType);

    boolean isValidFileType(String contentType);
}
