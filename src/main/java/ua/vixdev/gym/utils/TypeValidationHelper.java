package ua.vixdev.gym.utils;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.dto.RequestTypeDto;
import ua.vixdev.gym.entity.TypeEntity;
import ua.vixdev.gym.exception.TypeNotFoundException;
import ua.vixdev.gym.exception.TypeValueTooLong;

import java.util.Optional;

@Component
public class TypeValidationHelper {

    public void checkExistenceTypeEntity(Optional<TypeEntity> typeEntity, Long id) {
        if (typeEntity.isEmpty()) {
            throw new TypeNotFoundException("type with id %d was not found".formatted(id));
        }
    }

    public void checkTypeValueLength(RequestTypeDto requestTypeDto) {
        if (requestTypeDto.getValue().length() > 70) {
            throw new TypeValueTooLong("length of type value is too long");
        }
    }

    public void validateCreatedAtValue(RequestTypeDto requestTypeDto, TypeEntity typeEntity) {
        if (requestTypeDto.getCreatedAt() == null) {
            requestTypeDto.setCreatedAt(typeEntity.getCreatedAt());
        }
    }
}
