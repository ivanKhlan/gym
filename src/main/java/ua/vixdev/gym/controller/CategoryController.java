package ua.vixdev.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.dto.RequestCategoryDto;
import ua.vixdev.gym.entity.CategoryEntity;
import ua.vixdev.gym.service.CategoryService;
import ua.vixdev.gym.utils.CategoryValidationHelper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category/")
@RequiredArgsConstructor
@Log4j2
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryValidationHelper categoryValidationHelper;

    private final static String GET_CATEGORY_BY_ID = "{id}/";
    private final static String PUT_CATEGORY_BY_ID = "{id}/";
    private final static String DELETE_CATEGORY_BY_ID = "{id}/";

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> getAllCategoryEntities() {
        List<CategoryEntity> categoryEntities = categoryService.findAllCategoryEntities();

        return ResponseEntity.ok(categoryEntities);
    }

    @GetMapping(GET_CATEGORY_BY_ID)
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Long id) {
        Optional<CategoryEntity> categoryEntityById = categoryService.findCategoryEntityById(id);

        categoryValidationHelper.checkExistenceOfCategoryOption(categoryEntityById, id);

        return ResponseEntity.ok(categoryEntityById.get());
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody RequestCategoryDto requestCategoryDto) {

        categoryValidationHelper.checkCategoryValueLength(requestCategoryDto);

        CategoryEntity categoryEntity = categoryService.saveCategoryEntity(new CategoryEntity(
                requestCategoryDto.getValue(),
                requestCategoryDto.getVisible(),
                requestCategoryDto.getCreatedAt(),
                requestCategoryDto.getUpdatedAt(),
                requestCategoryDto.getDeletedAt()
        ));

        log.info("category with id: %d was create".formatted(categoryEntity.getId()));
        return ResponseEntity.ok("category with id: %d was created".formatted(categoryEntity.getId()));
    }

    @PutMapping(PUT_CATEGORY_BY_ID)
    public ResponseEntity<String> updateCategoryEntity(@PathVariable Long id, @RequestBody RequestCategoryDto requestCategoryDto) {
        Optional<CategoryEntity> categoryEntityById = categoryService.findCategoryEntityById(id);

        categoryValidationHelper.checkExistenceOfCategoryOption(categoryEntityById, id);

        if (requestCategoryDto.getCreatedAt() == null) {
            requestCategoryDto.setCreatedAt(categoryEntityById.get().getCreatedAt());
        }

        CategoryEntity updatedCategoryEntity = new CategoryEntity(
                id,
                requestCategoryDto.getValue(),
                requestCategoryDto.getVisible(),
                requestCategoryDto.getCreatedAt(),
                Instant.now(),
                requestCategoryDto.getDeletedAt()
        );

        categoryService.saveCategoryEntity(updatedCategoryEntity);

        return ResponseEntity.ok("category with id: %d was successfully updated".formatted(id));
    }

    @DeleteMapping(DELETE_CATEGORY_BY_ID)
    public ResponseEntity<String> deleteCategoryEntityById(@PathVariable Long id) {
        Optional<CategoryEntity> categoryEntityById = categoryService.findCategoryEntityById(id);

        categoryValidationHelper.checkExistenceOfCategoryOption(categoryEntityById, id);

        categoryService.deleteCategoryEntityById(id);

        return ResponseEntity.ok("category with id: %d was successfully deleted".formatted(id));
    }
}
