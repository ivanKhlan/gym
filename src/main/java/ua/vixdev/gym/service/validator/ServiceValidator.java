package ua.vixdev.gym.service.validator;


import ua.vixdev.gym.service.entity.ServiceEntity;
import ua.vixdev.gym.service.exceptions.DescriptionIsEmptyOrNullException;
import ua.vixdev.gym.service.exceptions.ImageIsEmptyOrNullException;
import ua.vixdev.gym.service.exceptions.ServiceIsEmptyException;
import ua.vixdev.gym.service.exceptions.TitleIsEmptyOrNullException;

/**
 * Utility class for validating service entities.
 */
public class ServiceValidator {
    /**
     * Validates the given service entity to ensure that it meets certain criteria.
     * Throws exceptions if the service entity is null, or if its title, description, or image is empty or null.
     * @param serviceEntity The service entity to validate.
     */
 public static void serviceValidate(ServiceEntity serviceEntity) {
    if(serviceEntity == null){

      throw new ServiceIsEmptyException();

    } else if(serviceEntity.getTitle() == null || serviceEntity.getTitle().isEmpty() ){

        throw new TitleIsEmptyOrNullException();

    } else if (serviceEntity.getDescription() == null || serviceEntity.getDescription().isEmpty()) {

        throw new DescriptionIsEmptyOrNullException();

    } else if (serviceEntity.getImage() == null || serviceEntity.getImage().isEmpty()) {

        throw new ImageIsEmptyOrNullException();
    }
 }

}
