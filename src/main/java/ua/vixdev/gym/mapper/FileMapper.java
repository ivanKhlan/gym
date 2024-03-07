package ua.vixdev.gym.mapper;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.entity.Files;
import ua.vixdev.gym.entity.FolderTypes;

import java.time.LocalDateTime;

@Component
public class FileMapper {

    public Files mapMultipartFileToEntity(String name, FolderTypes folder) {
        return Files.builder()
                .name(name)
                .visible(true)
                .folder(folder)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
