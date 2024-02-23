package service.validator;


import service.entity.EntityService;
import service.exceptions.DescriptionIsEmptyOrNullException;
import service.exceptions.ImageIsEmptyOrNullException;
import service.exceptions.ServiceIsEmptyException;
import service.exceptions.TitleIsEmptyOrNullException;

public class ServiceValidator {

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
