package service.mapper;

import org.springframework.stereotype.Component;
import service.dto.ServiceDto;
import service.entity.EntityService;
import service.exceptions.ServiceIsEmptyException;

import java.time.LocalDateTime;
/**
 * Mapper class responsible for mapping between EntityService and ServiceDto objects.
 */
@Component
public class ServiceMapper {
    /**
     * Maps an EntityService object to a ServiceDto object.
     * @param service The EntityService object to map.
     * @return The mapped ServiceDto object.
     */
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
    /**
     * Maps a ServiceDto object to an EntityService object.
     * @param serviceDto The ServiceDto object to map.
     * @return The mapped EntityService object.
     */
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


    /**
     * Updates an EntityService object with data from a ServiceDto object.
     * @param entityService The EntityService object to update.
     * @param service The ServiceDto object containing updated data.
     * @return The updated EntityService object.
     * @throws ServiceIsEmptyException if the input ServiceDto object is null.
     */
    public EntityService updateService(EntityService entityService, ServiceDto service){
        // Check if the input ServiceDto object is null
        if(service == null){
            // If the ServiceDto object is null, throw a ServiceIsEmptyException
            throw new ServiceIsEmptyException();
        }
        // Convert the ServiceDto object to an EntityService object
       var updatedService =  dtoToService(service);
        // Variables to hold the updated values for each field
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
       if(updatedService.getText() != null && !updatedService.getText().isEmpty()) {
          textNew = updatedService.getText();
       }else {
          textNew = entityService.getText();
       }

       if (updatedService.getImage() != null && !updatedService.getImage().isEmpty()){
           image = updatedService.getImage();
       }else{
           image = entityService.getImage();
       }

       if(updatedService.getTitle() != null && !updatedService.getTitle().isEmpty() ){
           title = updatedService.getTitle();
       }else{
           title = entityService.getTitle();
       }

       if(updatedService.getDescription() != null && !updatedService.getDescription().isEmpty()){
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
       // Build and return a new EntityService object with the updated values
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
    /**
     * Soft deletes an EntityService object by setting the deletedAt field.
     * @param entityService The EntityService object to soft delete.
     * @return The EntityService object with the deletedAt field set.
     */
    public EntityService softDeleted(EntityService entityService){

        return EntityService.builder()
                .id(entityService.getId())
                .deletedAt(LocalDateTime.now()).build();
    }
}
