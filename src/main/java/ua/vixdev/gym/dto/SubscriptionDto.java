package ua.vixdev.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
        private Long id;
        private String description;
        private boolean freezing;
        private double price;
        private String image;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private LocalDateTime daysFreezing;
        private LocalDateTime expirationAt;
        private boolean discount;
        private LocalDateTime discountSum;
        private LocalDateTime discountDate;
        private String text;
        private String title;
        private int statusId;
        private String lastName;


}
