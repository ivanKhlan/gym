package ua.vixdev.gym.reset_password;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;
import ua.vixdev.gym.reset_password.dto.NewPasswordRequest;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectValidator <T> {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public Set<String> validateObject(T newPasswordRequest) {
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(newPasswordRequest);

        if (!constraintViolationSet.isEmpty()) {
        return constraintViolationSet.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());

        }
        return Collections.emptySet();
    }
}
