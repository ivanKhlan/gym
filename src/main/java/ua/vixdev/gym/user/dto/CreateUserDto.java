package ua.vixdev.gym.user.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.mapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:messages/api_user_error_messages.properties")
public class CreateUserDto implements ModelMapper {

    @NotBlank(message = "{user.firstName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.firstName.invalid}")
    private String firstName;
    @NotBlank(message = "{user.lastName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.lastName.invalid}")
    private String lastName;
    @Email(message = "{user.email.invalid}")
    private String email;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "{user.password.invalid}")
    private String password;

    @Pattern(regexp = "((\\+38)?\\(?\\d{3}\\)?[\\s\\.-]?(\\d{7}|\\d{3}[\\s\\.-]\\d{2}[\\s\\.-]\\d{2}|\\d{3}-\\d{4}))",
    message = "{user.phoneNumber.invalid}")
    private String phoneNumber;
    private Boolean visible;

    @Override
    public UserEntity convertDtoToUserEntity() {
        return new UserEntity(
                firstName,
                lastName,
                email,
                password,
                phoneNumber,
                visible
        );
    }
}
