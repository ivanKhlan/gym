package ua.vixdev.gym.upload.dto;

public record FileInfoDTO(
        Long fileId,
        String fileName,
        String absPath,
        String contentType,
        String fileSize
) {}
