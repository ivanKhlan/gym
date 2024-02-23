package ua.vixdev.gym.factory;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.dto.ResponseTypeDto;
import ua.vixdev.gym.entity.TypeEntity;

@Component
public class ResponseTypeDtoFactory {

    public ResponseTypeDto makeResponseTypeDto(TypeEntity typeEntity) {
        return ResponseTypeDto.builder()
                .value(typeEntity.getValue())
                .visible(typeEntity.getVisible())
                .createdAt(typeEntity.getCreatedAt())
                .updatedAt(typeEntity.getUpdatedAt())
                .deletedAt(typeEntity.getDeletedAt())
                .build();
    }
}
