package ua.vixdev.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
/**
 * Data Transfer Object that represents request of history change
 */
@Builder
@Data
@AllArgsConstructor
public class HistoryChangesRequestDto {

    private Integer userId;

    private String text;

}
