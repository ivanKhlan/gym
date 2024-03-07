package ua.vixdev.gym.dto;

import java.time.LocalDateTime;

public record FileRepresentationDTO(
   Long id,
   String fileName,
   String filePath,
   LocalDateTime createdAt,
   LocalDateTime updatedAt,
   String fileArr
) {}
