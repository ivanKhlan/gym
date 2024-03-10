package ua.vixdev.gym.dto;

public record FileInfoDTO(
        String fileName,
        String absPath,
        String contentType,
        String fileSize
) {}
