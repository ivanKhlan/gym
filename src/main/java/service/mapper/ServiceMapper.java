package service.mapper;

import service.dto.ServiceDto;
import service.entity.EntityService;
import service.exceptions.ServiceIsEmptyException;

import java.time.LocalDateTime;

public class ServiceMapper {
    public  ServiceDto serviceToDto(EntityService service){
        if(service == null){
            return null;
        }
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

    public EntityService dtoToService(ServiceDto serviceDto){
        if(serviceDto == null){
            return null;
        }

        return EntityService.builder()
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


    public EntityService updateService(EntityService entityService,ServiceDto service){
       if(service == null){
           throw new ServiceIsEmptyException();
       }

       var updatedService =  dtoToService(service);

       String textNew;

       String description;

       double price;

       int counts;

       LocalDateTime endDate;

       LocalDateTime startDate;

       String title;

       String image;

       if(!updatedService.getText().isEmpty() && updatedService.getText() != null) {
          textNew = entityService.getText();
       }else {
          textNew = entityService.getText();
       }

       if (!updatedService.getImage().isEmpty() && updatedService.getImage() != null){
           image = updatedService.getImage();
       }else{
           image = entityService.getImage();
       }

       if(!updatedService.getTitle().isEmpty() && updatedService.getTitle() != null){
           title = updatedService.getTitle();
       }else{
           title = entityService.getTitle();
       }

       if(!updatedService.getDescription().isEmpty() && updatedService.getDescription() != null){
          description = updatedService.getDescription();
       }else{
          description = entityService.getDescription();
       }

       if(updatedService.getPrice() != 0.0 && updatedService.getPrice() < 0.0 ){
           price = updatedService.getPrice();
       }else{
           price = entityService.getPrice();
       }

       if(updatedService.getCounts() != 0 && updatedService.getCounts() < 0){
           counts = updatedService.getCounts();
       }else{
           counts = entityService.getCounts();
       }

       if(updatedService.getEndDate() != null){
           endDate = updatedService.getEndDate();
       }else {
           endDate = entityService.getEndDate();
       }

       if(updatedService.getStartDate() != null && !updatedService.getStartDate().isAfter(endDate)){
           startDate = updatedService.getStartDate();
       }else {
           startDate = entityService.getStartDate();
       }

       return EntityService.builder()
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

    public EntityService softDeleted(EntityService entityService){

        return EntityService.builder()
                .id(entityService.getId())
                .deletedAt(LocalDateTime.now()).build();
    }
}
