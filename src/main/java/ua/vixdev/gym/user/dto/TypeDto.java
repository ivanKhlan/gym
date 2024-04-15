package ua.vixdev.gym.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.user.entity.TypeEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDto {

    @NotBlank(message = "{user.firstName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.firstName.invalid}")
    private String title;

    private Boolean visible;


    public TypeEntity toGenderEntity() {
        return new TypeEntity(title, visible);
    }
}
