package ua.vixdev.gym.user.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.mapper.ModelMapper;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto implements ModelMapper {
    @NotBlank(message = "{user.firstName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.lastName.invalid}")
    private String firstName;
    @NotBlank(message = "{user.lastName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.lastName.invalid}")
    private String lastName;
    @Email(regexp = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$",
            message = "{user.email.invalid}")
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
