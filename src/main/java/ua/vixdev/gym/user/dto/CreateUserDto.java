package ua.vixdev.gym.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.user.entity.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto implements ModelMapper {

    @NotNull(message = "{firstName.notNull}")
    @Size(min = 2, max = 70, message = "{firstName.range}")
    private String firstName;
    @NotNull(message = "{lastName.notNull}")
    @Size(min = 2, max = 70, message = "{lastName.range}")
    private String lastName;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "{password.regex}")
    private String password;
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
