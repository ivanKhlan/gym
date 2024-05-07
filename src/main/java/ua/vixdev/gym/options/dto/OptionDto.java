package ua.vixdev.gym.options.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
    @NotNull(message = "{option.key.notBlank}")
    @Size(max = 70, message = "{option.key.invalid}")
    private String key;

    @NotBlank
    @NotNull
    private String value;
    private boolean visible;
    private boolean autoload;
}
