package service.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO class representing a service.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ServiceDto {

    private String title;

    private String description;

    private double price;

    private int counts;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String text;

    private String image;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
