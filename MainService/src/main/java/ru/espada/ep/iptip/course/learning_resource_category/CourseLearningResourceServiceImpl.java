package ru.espada.ep.iptip.course.learning_resource_category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.espada.ep.iptip.course.learning_resource_category.folder.CourseLearningResourceFolderEntity;
import ru.espada.ep.iptip.course.learning_resource_category.folder.CourseLearningResourceFolderRepository;
import ru.espada.ep.iptip.course.learning_resource_category.model.CourseLearningResourceEntityDto;
import ru.espada.ep.iptip.course.learning_resource_category.model.CreateCourseLEarningFolderModel;
import ru.espada.ep.iptip.course.learning_resource_category.model.CreateCourseLearningResourceModel;
import ru.espada.ep.iptip.course.learning_resource_category.resource.CourseLearningResourceEntity;
import ru.espada.ep.iptip.course.learning_resource_category.resource.CourseLearningResourceRepository;
import ru.espada.ep.iptip.s3.S3Service;
import ru.espada.ep.iptip.user.UserEntity;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CourseLearningResourceServiceImpl implements CourseLearningResourceService {

    private CourseLearningResourceCategoryRepository courseLearningResourceCategoryRepository;
    private CourseLearningResourceRepository courseLearningResourceRepository;
    private CourseLearningResourceFolderRepository courseLearningResourceFolderRepository;
    private S3Service s3Service;

    @Override
    @Transactional
    public List<CourseLearningResourceEntityDto> getCourseLearningResource(Long categoryId) {
        return courseLearningResourceRepository.findCourseLearningResourceEntitiesByCategory(CourseLearningResourceCategoryEntity.builder().id(categoryId).build()).stream()
                .map(courseLearningResourceEntity -> CourseLearningResourceEntityDto.builder()
                        .id(courseLearningResourceEntity.getId())
                        .type(courseLearningResourceEntity.getType())
                        .name(courseLearningResourceEntity.getName())
                        .content(courseLearningResourceEntity.getContent())
                        .folderName(courseLearningResourceEntity.getFolder() == null ? null : courseLearningResourceEntity.getFolder().getName())
                        .build()
                ).toList();
    }

    @Override
    @Transactional
    public Long createResource(CreateCourseLearningResourceModel createCourseLearningResourceModel) {
        CourseLearningResourceEntity courseLearningResourceEntity = CourseLearningResourceEntity.builder()
                .name(createCourseLearningResourceModel.getName())
                .type(createCourseLearningResourceModel.getType())
                .content(createCourseLearningResourceModel.getContent())
                .folder(CourseLearningResourceFolderEntity.builder().id(createCourseLearningResourceModel.getFolderId()).build())
                .build();
        return courseLearningResourceRepository.save(courseLearningResourceEntity).getId();
    }

    @Override
    public void deleteResource(Long id) {
        courseLearningResourceRepository.deleteById(id);
    }

    @Override
    public Long createFolder(CreateCourseLEarningFolderModel createCourseLEarningFolderModel) {
        CourseLearningResourceFolderEntity courseLearningResourceFolderEntity = CourseLearningResourceFolderEntity.builder()
                .name(createCourseLEarningFolderModel.getName())
                .build();
        courseLearningResourceFolderRepository.save(courseLearningResourceFolderEntity);
        return courseLearningResourceFolderEntity.getId();
    }

    @Override
    public CompletableFuture<String> uploadFile(byte[] avatar, String contentType) {
        if (!isValidFileType(contentType)) {
            throw new IllegalArgumentException("exception.resource.upload.unsupported.file.type");
        }

        UUID uuid = UUID.randomUUID();
        return s3Service.uploadPng(avatar, uuid.toString(), "resources")
                .thenCompose(fileName -> {
                    if (fileName == null) {
                        throw new IllegalArgumentException("exception.resource.upload.failed");
                    } else {
                        return s3Service.getFileUrl("resources", fileName);
                    }
                }).thenApply(url -> url);
    }

    @Override
    public boolean isValidFileType(String contentType) {
        return contentType.equals("application/pdf") ||
                contentType.equals("application/pptx") ||
                contentType.equals("application/docx") ||
                contentType.equals("application/xlsx") ||
                contentType.equals("application/txt");
    }

    @Autowired
    public void setCourseLearningResourceCategoryRepository(CourseLearningResourceCategoryRepository courseLearningResourceCategoryRepository) {
        this.courseLearningResourceCategoryRepository = courseLearningResourceCategoryRepository;
    }

    @Autowired
    public void setCourseLearningResourceRepository(CourseLearningResourceRepository courseLearningResourceRepository) {
        this.courseLearningResourceRepository = courseLearningResourceRepository;
    }

    @Autowired
    public void setCourseLearningResourceFolderRepository(CourseLearningResourceFolderRepository courseLearningResourceFolderRepository) {
        this.courseLearningResourceFolderRepository = courseLearningResourceFolderRepository;
    }

    @Autowired
    public void setS3Service(S3Service s3Service) {
        this.s3Service = s3Service;
    }
}
