package ua.vixdev.gym.mapper;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.dto.CreateFolderDTO;
import ua.vixdev.gym.dto.FolderDto;
import ua.vixdev.gym.entity.FolderTypes;

import java.time.LocalDateTime;

@Component
public class FolderTypesMapper {

    public FolderDto mapEntityToDto(FolderTypes folder) {
        if (folder == null)
            return null;
        return FolderDto.builder()
                .id(folder.getId())
                .title(folder.getTitle())
                .createdAt(folder.getCreatedAt())
                .updatedAt(folder.getUpdatedAt())
                .build();
    }

    public FolderTypes mapDtoToEntity(CreateFolderDTO folder) {
        return FolderTypes.builder()
                .title(folder.getTitle())
                .createdAt(LocalDateTime.now())
                .visible(true)
                .build();
    }
}
