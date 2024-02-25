package ua.vixdev.gym.user.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.utils.PasswordEncoders;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    private static final String EMAIL_PATTERN = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
    private static final String PHONE_PATTERN = "((\\+38)?\\(?\\d{3}\\)?[\\s\\.-]?(\\d{7}|\\d{3}[\\s\\.-]\\d{2}[\\s\\.-]\\d{2}|\\d{3}-\\d{4}))";
    @NotBlank(message = "{user.firstName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.firstName.invalid}")
    private String firstName;
    @NotBlank(message = "{user.lastName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.lastName.invalid}")
    private String lastName;
    @Email(regexp = EMAIL_PATTERN, message = "{user.email.invalid}")
    private String email;
    @Pattern(regexp = PASSWORD_PATTERN, message = "{user.password.invalid}")
    private String password;
    @Pattern(regexp = PHONE_PATTERN, message = "{user.phoneNumber.invalid}")
    private String phoneNumber;
    private Boolean visible;

    public UserEntity toUserEntity() {
        return new UserEntity(
                firstName,
                lastName,
                email,
                PasswordEncoders.encodePassword(password),
                phoneNumber,
                visible
        );
    }
}
