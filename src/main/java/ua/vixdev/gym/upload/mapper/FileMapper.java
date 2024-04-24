package ua.vixdev.gym.upload.mapper;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.upload.dto.FileRepresentationDTO;
import ua.vixdev.gym.upload.entity.UploadFilesEntity;
import ua.vixdev.gym.upload.entity.FolderTypesEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class FileMapper {

    public UploadFilesEntity mapMultipartFileToEntity(String name, FolderTypesEntity folder) {
        LocalDateTime currentTime = LocalDateTime.now();
        return UploadFilesEntity.builder()
                .name(name)
                .visible(true)
                .folder(folder)
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
    }

    public FileRepresentationDTO mapFileEntityToDTO(UploadFilesEntity file) throws IOException {
        return new FileRepresentationDTO(file.getId(),
                file.getName(),
                file.getFolder().getTitle(),
                file.getCreatedAt(),
                file.getUpdatedAt(),
                Arrays.toString(Files.readAllBytes(Path.of(file.getFullPath())))
        );
    }
}
