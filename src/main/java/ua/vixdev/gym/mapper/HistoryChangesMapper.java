package ua.vixdev.gym.mapper;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.dto.HistoryChangesResponseDto;
import ua.vixdev.gym.entity.HistoryChangesEntity;

/**
 * Class-mapper for creating Response DTO of history change
 */
@Component
public class HistoryChangesMapper {

    /**
     * method for converting response DTO of history change
     * @param historyChangesEntity - history change entity representing data for response DTO
     * @return created HistoryChangesResponse
     */
    public HistoryChangesResponseDto makeHistoryChangesDto(HistoryChangesEntity historyChangesEntity) {
        return HistoryChangesResponseDto.builder()
                .userId(historyChangesEntity.getUserId())
                .text(historyChangesEntity.getText())
                .createdAt(historyChangesEntity.getCreatedAt())
                .build();
    }
}
