package ua.vixdev.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.dto.RequestCategoryDto;
import ua.vixdev.gym.dto.ResponseCategoryDto;
import ua.vixdev.gym.entity.CategoryEntity;
import ua.vixdev.gym.factory.ResponseCategoryDtoFactory;
import ua.vixdev.gym.service.CategoryService;
import ua.vixdev.gym.utils.CategoryValidationHelper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
*    Rest controller for managing category
*/

@RestController
@RequestMapping("/category/")
@RequiredArgsConstructor
@Log4j2
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryValidationHelper categoryValidationHelper;
    private final ResponseCategoryDtoFactory categoryDtoFactory;

    private final static String GET_CATEGORY_BY_ID = "{id}/";
    private final static String PUT_CATEGORY_BY_ID = "{id}/";
    private final static String DELETE_CATEGORY_BY_ID = "{id}/";

    /**
     * Retrieves all categories
     *
     * @return Lit of existed categories
     */
    @GetMapping
    public ResponseEntity<List<ResponseCategoryDto>> getAllCategoryEntities() {
        List<CategoryEntity> categoryEntities = categoryService.findAllCategoryEntities();

        List<ResponseCategoryDto> responseCategoryDtoFactories = categoryEntities
                .stream()
                .map(categoryDtoFactory::makeResponseCategoryDto)
                .toList();

        return ResponseEntity.ok(responseCategoryDtoFactories);
    }

    /**
     *
     * @param id Represents category id
     * @return existed category
     */
    @GetMapping(GET_CATEGORY_BY_ID)
    public ResponseEntity<ResponseCategoryDto> getCategoryById(@PathVariable Long id) {
        Optional<CategoryEntity> categoryEntityById = categoryService.findCategoryEntityById(id);

        categoryValidationHelper.checkExistenceOfCategoryOption(categoryEntityById, id);

        ResponseCategoryDto response = categoryDtoFactory.makeResponseCategoryDto(categoryEntityById.get());

        return ResponseEntity.ok(response);
    }

    /**
     *
     * @param requestCategoryDto awaits RequestCategoryDto to be created
     * @return String of confirmation
     */
    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody RequestCategoryDto requestCategoryDto) {

        categoryValidationHelper.checkCategoryValueLength(requestCategoryDto);

        CategoryEntity categoryEntity = categoryService.saveCategoryEntity(
                new CategoryEntity(
                        requestCategoryDto.getValue(),
                        requestCategoryDto.getVisible(),
                        requestCategoryDto.getCreatedAt(),
                        requestCategoryDto.getUpdatedAt(),
                        requestCategoryDto.getDeletedAt()
                )
        );

        log.info("category with id: %d was create".formatted(categoryEntity.getId()));
        return ResponseEntity.ok("category with id: %d was created".formatted(categoryEntity.getId()));
    }

    /**
     *
     * @param id Represents id of category to be changed
     * @param requestCategoryDto Represents updated category
     * @return String of confirmation
     */
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

    /**
     *
     * @param id Represents id of category to be deleted
     * @return String of confirmation
     */
    @DeleteMapping(DELETE_CATEGORY_BY_ID)
    public ResponseEntity<String> deleteCategoryEntityById(@PathVariable Long id) {
        Optional<CategoryEntity> categoryEntityById = categoryService.findCategoryEntityById(id);

        categoryValidationHelper.checkExistenceOfCategoryOption(categoryEntityById, id);

        CategoryEntity existedCategoryEntity = categoryEntityById.get();
        existedCategoryEntity.setDeletedAt(Instant.now());
        existedCategoryEntity.setVisible(false);

        categoryService.saveCategoryEntity(existedCategoryEntity);

        return ResponseEntity.ok("category with id: %d was successfully deleted".formatted(id));
    }
}
