package ua.vixdev.gym.status.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    @NotNull(message = "Status value must not be null")
    @Size(max = 70, message = "Status value must be less than 70 characters")
    private String value;
    private boolean visible;
}
