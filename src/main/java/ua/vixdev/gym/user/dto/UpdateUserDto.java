package ua.vixdev.gym.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.user.entity.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto implements ModelMapper{
    private String firstName;
    private String lastName;
    private String email;
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
