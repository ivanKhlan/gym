package ua.vixdev.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.vixdev.gym.category.controller.dto.CategoryController;
import ua.vixdev.gym.category.controller.dto.CategoryDto;
import ua.vixdev.gym.category.entity.CategoryEntity;
import ua.vixdev.gym.category.service.CategoryService;
import commons.config.utils.CategoryValidationHelper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CategoryService categoryService;

    @MockBean
    CategoryValidationHelper categoryValidationHelper;

    @MockBean
    ResponseCategoryDtoFactory responseCategoryDtoFactory;

    private static final String END_POINT = "/category/";

    private CategoryEntity categoryEntity;


    @BeforeEach
    void setUp() {
        categoryEntity = new CategoryEntity(1L, "test", true, Instant.now(), Instant.now(), null);
        MockitoAnnotations.openMocks(this);
    }

    // retrieves all categories
    @Test
    void getAllCategoryEntities_Success() throws Exception {
        List<CategoryEntity> categoryEntities = List.of(categoryEntity, categoryEntity, categoryEntity);

        when(categoryService.findAll()).thenReturn(categoryEntities);

        mockMvc.perform(get(END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntities)))
                .andExpect(status().isOk())
                .andReturn();
    }

    // get category by id
    @Test
    void getCategoryById_Success() throws Exception {
        Optional<CategoryEntity> optionalCategoryEntity = Optional.of(categoryEntity);

        when(categoryService.findById(1L)).thenReturn(optionalCategoryEntity);

        mockMvc.perform(get(END_POINT + "1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity)))
                .andExpect(status().isOk())
                .andReturn();
    }

    // create category
    @Test
    void createCategory_Success() throws Exception {
        CategoryDto categoryDto = new CategoryDto("test", true, Instant.now(), Instant.now(), null);

        when(categoryService.updateCategory(any(CategoryEntity.class)))
                .thenReturn(categoryEntity);

        mockMvc.perform(post(END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("category with id: 1 was created"));
    }

    // category description (value) is too long
    @Test
    void createCategory_DescriptionTooLong() throws Exception {
        String longDescription = "a".repeat(71);
        CategoryDto categoryDto = new CategoryDto(longDescription, true, Instant.now(), Instant.now(), null);

        doThrow(new CategoryLengthTooLong("length of category description is too long"))
                .when(categoryValidationHelper).checkCategoryValueLength(categoryDto);

        mockMvc.perform(post(END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isBadRequest());
    }

    // updates category
    @Test
    void updateCategoryEntity_Success() throws Exception {
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto("test", true, Instant.now(), Instant.now(), null);
        CategoryEntity categoryEntity = new CategoryEntity(categoryId, "test", true, Instant.now(), Instant.now(), null);


        when(categoryService.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(categoryService.updateCategory(any(CategoryEntity.class))).thenReturn(categoryEntity);

        mockMvc.perform(put(END_POINT + "{id}/", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("category with id: " + categoryId + " was successfully updated")));

    }

    // deletes category
    @Test
    void deleteCategoryEntityById_Success() throws Exception {
        Long categoryId = 1L;
        CategoryEntity categoryEntity = new CategoryEntity(categoryId, "test", true, Instant.now(), Instant.now(), null);

        when(categoryService.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        doNothing().when(categoryService).deleteById(categoryId);

        mockMvc.perform(delete(END_POINT + "{id}/", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("category with id: " + categoryId + " was successfully deleted")));
    }
}
