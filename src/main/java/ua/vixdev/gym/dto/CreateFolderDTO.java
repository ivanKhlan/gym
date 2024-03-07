package ua.vixdev.gym.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateFolderDTO {

    @NotNull(message = "Title field should be present.")
    @NotBlank(message = "Title field should not be empty.")
    @Size(max = 70, message = "Directory name should contain less then 70 symbols.")
    private String title;
}
