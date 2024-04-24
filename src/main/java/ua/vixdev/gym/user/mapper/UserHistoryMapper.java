package ua.vixdev.gym.user.mapper;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.user.controller.dto.UserHistoryResponse;
import ua.vixdev.gym.user.entity.UserHistoryEntity;

/**
 * Class-mapper for creating Response DTO of history change
 */
@Component
public class UserHistoryMapper {

    /**
     * method for converting response DTO of history change
     * @param userHistoryEntity - history change entity representing data for response DTO
     * @return created HistoryChangesResponse
     */
    public UserHistoryResponse makeHistoryChangesDto(UserHistoryEntity userHistoryEntity) {
        return UserHistoryResponse.builder()
                .userId(userHistoryEntity.getUserId())
                .text(userHistoryEntity.getText())
                .createdAt(userHistoryEntity.getCreatedAt())
                .build();
    }
}
