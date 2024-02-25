package ua.vixdev.gym.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
