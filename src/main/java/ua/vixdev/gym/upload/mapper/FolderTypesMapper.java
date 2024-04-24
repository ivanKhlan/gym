package ua.vixdev.gym.upload.mapper;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.upload.dto.CreateFolderDTO;
import ua.vixdev.gym.upload.dto.FolderDto;
import ua.vixdev.gym.upload.entity.FolderTypesEntity;

import java.time.LocalDateTime;

@Component
public class FolderTypesMapper {

    public FolderDto mapEntityToDto(FolderTypesEntity folder) {
        return FolderDto.builder()
                .id(folder.getId())
                .title(folder.getTitle())
                .createdAt(folder.getCreatedAt())
                .updatedAt(folder.getUpdatedAt())
                .build();
    }

    public FolderTypesEntity mapDtoToEntity(CreateFolderDTO folder) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return FolderTypesEntity.builder()
                .title(folder.getTitle())
                .createdAt(currentDateTime)
                .updatedAt(currentDateTime)
                .visible(true)
                .build();
    }
}
