package ua.vixdev.gym.mapper;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.dto.SubscriptionDto;
import ua.vixdev.gym.entity.Subscription;
@Component
public class SubscriptionMapper {
    public SubscriptionDto toDTO(Subscription subscription) {
        SubscriptionDto dto = new SubscriptionDto();
        dto.setId(subscription.getId());
        dto.setDescription(subscription.getDescription());
        dto.setFreezing(subscription.isFreezing());
        dto.setPrice(subscription.getPrice());
        dto.setImage(subscription.getImage());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setDaysFreezing(subscription.getDaysFreezing());
        dto.setExpirationAt(subscription.getExpirationAt());
        dto.setDiscount(subscription.isDiscount());
        dto.setDiscountSum(subscription.getDiscountSum());
        dto.setDiscountDate(subscription.getDiscountDate());
        dto.setText(subscription.getText());
        dto.setTitle(subscription.getTitle());
        dto.setStatusId(subscription.getStatusId());
        dto.setLastName(subscription.getLastName());
        return dto;
    }

    public Subscription toEntity(SubscriptionDto dto) {
        Subscription subscription = new Subscription();
        subscription.setId(dto.getId());
        subscription.setDescription(dto.getDescription());
        subscription.setFreezing(dto.isFreezing());
        subscription.setPrice(dto.getPrice());
        subscription.setImage(dto.getImage());
        subscription.setStartDate(dto.getStartDate());
        subscription.setEndDate(dto.getEndDate());
        subscription.setDaysFreezing(dto.getDaysFreezing());
        subscription.setExpirationAt(dto.getExpirationAt());
        subscription.setDiscount(dto.isDiscount());
        subscription.setDiscountSum(dto.getDiscountSum());
        subscription.setDiscountDate(dto.getDiscountDate());
        subscription.setText(dto.getText());
        subscription.setTitle(dto.getTitle());
        subscription.setStatusId(dto.getStatusId());
        subscription.setLastName(dto.getLastName());
        return subscription;
    }
}