package ua.vixdev.gym.category.controller.dto;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.category.entity.CategoryEntity;

@Component
public class ResponseCategoryDtoFactory {

    public ResponseCategoryDto makeResponseCategoryDto(CategoryEntity categoryEntity) {
        return ResponseCategoryDto.builder()
                .value(categoryEntity.getValue())
                .visible(categoryEntity.getVisible())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .deletedAt(categoryEntity.getDeletedAt())
                .build();
    }
}
