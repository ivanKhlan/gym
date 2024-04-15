package ua.vixdev.gym.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.user.entity.GenderEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderDto {

    @NotBlank(message = "{user.firstName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.firstName.invalid}")
    private String title;

    private Boolean visible;


    public GenderEntity toGenderEntity() {
        return new GenderEntity(title, visible);
    }
}
