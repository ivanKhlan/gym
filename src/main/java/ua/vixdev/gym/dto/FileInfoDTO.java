package ua.vixdev.gym.dto;

/**
 * Class to provide information about stored file.
 */
public record FileInfoDTO(
        String fileName,
        String fullFilePath,
        String contentType,
        String fileSize
) {}
