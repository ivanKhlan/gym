package ua.vixdev.gym.upload.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RenameFolderDTO (
        @NotNull(message = "Should contain 'newFolderName' field.")
        @NotBlank(message = "Files 'newFolderName' should not be empty.")
        @Size(max = 20, message = "Directory name should contain less then 20 symbols.")
        @Pattern(regexp = "^[A-Za-z_$\\-0-9 ]{1,20}$",
                message = "Folder name could include any alphabet letter, numbers, underscores and dashes, dollar signs and spaces.")
    String newFolderName
){}
