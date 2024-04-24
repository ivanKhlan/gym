package ua.vixdev.gym.upload.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateFolderDTO {

    /**
     * Currently supports only Windows folders (e.g. C:/vixdev/...)
     */
    @NotNull(message = "Title field should be present.")
    @NotBlank(message = "Title field should not be empty.")
    @Size(max = 70, message = "Directory name should contain less then 70 symbols.")
    @Pattern(regexp = "^[A-Z]:/[A-Za-z_0-9-]+(/[A-Za-z_0-9-]+)*/?$",
            message = "Folder name should start with the drive name (e.g. C:/) and contain at least one folder name after." +
                    "Available special symbols to use are '-_/', all others are not supported. Example: C:/vixdev/...")
    private String title;
}
