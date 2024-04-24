package ua.vixdev.gym.service.validator;


import ua.vixdev.gym.service.entity.EntityService;
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
     * @param entityService The service entity to validate.
     */
 public static void serviceValidate(EntityService entityService) {
    if(entityService == null){

      throw new ServiceIsEmptyException();

    } else if(entityService.getTitle() == null || entityService.getTitle().isEmpty() ){

        throw new TitleIsEmptyOrNullException();

    } else if (entityService.getDescription() == null || entityService.getDescription().isEmpty()) {

        throw new DescriptionIsEmptyOrNullException();

    } else if (entityService.getImage() == null || entityService.getImage().isEmpty()) {

        throw new ImageIsEmptyOrNullException();
    }
 }

}
