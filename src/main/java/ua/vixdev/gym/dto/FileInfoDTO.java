package ua.vixdev.gym.dto;

/**
 * Class to provide information about stored file.
 */
public record FileInfoDTO(
        String fileName,
        String absPath,
        String contentType,
        String fileSize
) {}
