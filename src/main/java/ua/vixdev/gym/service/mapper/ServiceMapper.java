package ua.vixdev.gym.service.mapper;

import ua.vixdev.gym.service.dto.ServiceDto;
import ua.vixdev.gym.service.entity.ServiceEntity;

import java.time.LocalDateTime;

/**
 * Mapper class responsible for mapping between EntityService and ServiceDto objects.
 */
public class ServiceMapper {

    public static ServiceDto serviceToDto(ServiceEntity service) {
        return ServiceDto.builder()
                .title(service.getTitle())
                .price(service.getPrice())
                .counts(service.getCounts())
                .endDate(service.getEndDate())
                .image(service.getImage())
                .startDate(service.getStartDate())
                .description(service.getDescription())
                .text(service.getText())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }

    public ServiceEntity dtoToService(ServiceDto serviceDto) {
        return ServiceEntity.builder()
                .title(serviceDto.getTitle())
                .description(serviceDto.getDescription())
                .counts(serviceDto.getCounts())
                .price(serviceDto.getPrice())
                .text(serviceDto.getText())
                .image(serviceDto.getImage())
                .endDate(serviceDto.getEndDate())
                .startDate(serviceDto.getStartDate())
                .createdAt(serviceDto.getCreatedAt())
                .updatedAt(serviceDto.getUpdatedAt())
                .build();
    }

    public ServiceEntity updateService(ServiceEntity serviceEntity, ServiceDto service) {
        var updatedService = dtoToService(service);
        String textNew;

        String description;

        double price;

        int counts;

        LocalDateTime endDate;

        LocalDateTime startDate;

        String title;

        String image;
        // Logic to update each field of the entityService object
        // if the corresponding field in the updatedService object is not null or empty
        if (updatedService.getText() != null && !updatedService.getText().isEmpty()) {
            textNew = updatedService.getText();
        } else {
            textNew = serviceEntity.getText();
        }

        if (updatedService.getImage() != null && !updatedService.getImage().isEmpty()) {
            image = updatedService.getImage();
        } else {
            image = serviceEntity.getImage();
        }

        if (updatedService.getTitle() != null && !updatedService.getTitle().isEmpty()) {
            title = updatedService.getTitle();
        } else {
            title = serviceEntity.getTitle();
        }

        if (updatedService.getDescription() != null && !updatedService.getDescription().isEmpty()) {
            description = updatedService.getDescription();
        } else {
            description = serviceEntity.getDescription();
        }

        if (updatedService.getPrice() != 0.0 && updatedService.getPrice() < 0.0) {
            price = updatedService.getPrice();
        } else {
            price = serviceEntity.getPrice();
        }

        if (updatedService.getCounts() != 0 && updatedService.getCounts() < 0) {
            counts = updatedService.getCounts();
        } else {
            counts = serviceEntity.getCounts();
        }

        if (updatedService.getEndDate() != null) {
            endDate = updatedService.getEndDate();
        } else {
            endDate = serviceEntity.getEndDate();
        }

        if (updatedService.getStartDate() != null && !updatedService.getStartDate().isAfter(endDate)) {
            startDate = updatedService.getStartDate();
        } else {
            startDate = serviceEntity.getStartDate();
        }
        // Build and return a new EntityService object with the updated values
        return ServiceEntity.builder()
                .updatedAt(LocalDateTime.now())
                .counts(counts)
                .title(title)
                .image(image)
                .price(price)
                .description(description)
                .text(textNew)
                .endDate(endDate)
                .startDate(startDate)
                .build();
    }

    public ServiceEntity softDeleted(ServiceEntity serviceEntity) {

        return ServiceEntity.builder()
                .id(serviceEntity.getId())
                .deletedAt(LocalDateTime.now()).build();
    }
}
