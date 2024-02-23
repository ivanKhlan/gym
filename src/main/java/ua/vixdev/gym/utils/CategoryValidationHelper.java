package ua.vixdev.gym.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ua.vixdev.gym.dto.RequestCategoryDto;
import ua.vixdev.gym.entity.CategoryEntity;
import ua.vixdev.gym.exception.CategoryEntityWasNotFound;
import ua.vixdev.gym.exception.CategoryLengthTooLong;

import java.util.Optional;

@Component
@Log4j2
public class CategoryValidationHelper {

    public void checkExistenceOfCategoryOption(Optional<CategoryEntity> categoryEntityOptional, Long id) {
        if (categoryEntityOptional.isEmpty()) {
            throw new CategoryEntityWasNotFound("category with id: %d was not found".formatted(id));
        }
    }

    public void checkCategoryValueLength(RequestCategoryDto requestCategoryDto) {
        if (requestCategoryDto.getValue().length() > 70) {
            log.error("length of category description is too long");
            throw new CategoryLengthTooLong("length of category description is too long");
        }
    }
}
