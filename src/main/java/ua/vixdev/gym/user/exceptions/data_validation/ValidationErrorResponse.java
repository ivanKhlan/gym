package ua.vixdev.gym.user.exceptions.data_validation;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-21
 */
@Data
@NoArgsConstructor
public class ValidationErrorResponse {
    private final List<Violation> violations = new ArrayList<>();
}
