package ru.espada.ep.iptip.course.learning_resource_category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.espada.ep.iptip.course.learning_resource_category.model.CreateCourseLEarningFolderModel;
import ru.espada.ep.iptip.course.learning_resource_category.model.CreateCourseLearningResourceModel;
import ru.espada.ep.iptip.course.learning_resource_category.model.CourseLearningResourceEntityDto;

import java.util.List;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/course/resource")
public class CourseLearningResourceController {

    private CourseLearningResourceService courseLearningResourceService;

    @GetMapping("/{categoryId}")
    @Operation(description = "Получает все ресурсы в категории курса. Надо самому разделить их на фронте по папкам.")
    public ResponseEntity<List<CourseLearningResourceEntityDto>> getCourseLearningResource(@PathVariable Long categoryId) {
        return ResponseEntity.ok(courseLearningResourceService.getCourseLearningResource(categoryId));
    }

    @PostMapping("")
    public ResponseEntity<Long> createResource(@Valid @RequestBody CreateCourseLearningResourceModel createCourseLearningResourceModel) {
        Long id = courseLearningResourceService.createResource(createCourseLearningResourceModel);
        return ResponseEntity.ok().body(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResource(@PathVariable Long id) {
        courseLearningResourceService.deleteResource(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/folder")
    public ResponseEntity<Long> createFolder(@Valid @RequestBody CreateCourseLEarningFolderModel createCourseLEarningFolderModel) {
        Long id = courseLearningResourceService.createFolder(createCourseLEarningFolderModel);
        return ResponseEntity.ok().body(id);
    }

    @PostMapping("/load/file")
    public ResponseEntity<String> loadFile(@RequestBody byte[] file, @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType) {
        String url = courseLearningResourceService.uploadFile(file, contentType).join();
        return ResponseEntity.ok(url);
    }

    @Autowired
    public void setCourseLearningResourceService(CourseLearningResourceService courseLearningResourceService) {
        this.courseLearningResourceService = courseLearningResourceService;
    }

}

