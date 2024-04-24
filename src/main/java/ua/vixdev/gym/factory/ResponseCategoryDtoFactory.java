package ua.vixdev.gym.factory;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.dto.ResponseCategoryDto;
import ua.vixdev.gym.entity.CategoryEntity;

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
