package ua.vixdev.gym.category.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.category.controller.dto.CategoryDto;
import ua.vixdev.gym.category.entity.CategoryEntity;
import ua.vixdev.gym.category.service.CategoryService;

import java.util.List;

/**
*    Rest controller for managing category
*/

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private static final Long EMPTY_ID = null;
    private final CategoryService categoryService;

    /**
     * Retrieves all categories
     *
     * @return Lit of existed categories
     */
    @GetMapping
    public List<CategoryEntity> findAll() {
        return categoryService.findAll();
    }

    /**
     *
     * @param id Represents category id
     * @return existed category
     */
    @GetMapping("/{id}")
    public CategoryEntity findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    /**
     *
     * @param categoryDto awaits RequestCategoryDto to be created
     * @return String of confirmation
     */
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CategoryEntity createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.createCategory(mapToCategory(EMPTY_ID, categoryDto));
    }

    /**
     *
     * @param id Represents id of category to be changed
     * @param categoryDto Represents updated category
     * @return String of confirmation
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CategoryEntity updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(mapToCategory(id, categoryDto));
    }

    /**
     *
     * @param id Represents id of category to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    private CategoryEntity mapToCategory(Long id, CategoryDto categoryDto) {
            return CategoryEntity.builder()
                    .id(id)
                    .value(categoryDto.getValue())
                    .visible(categoryDto.getVisible())
                    .build();
    }
}
