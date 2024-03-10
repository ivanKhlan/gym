package ua.vixdev.gym.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.vixdev.gym.dto.CreateFolderDTO;
import ua.vixdev.gym.dto.FolderDto;
import ua.vixdev.gym.entity.Files;
import ua.vixdev.gym.entity.FolderTypes;
import ua.vixdev.gym.exceptions.EntityNotFoundException;
import ua.vixdev.gym.exceptions.FolderAlreadyExistsException;
import ua.vixdev.gym.exceptions.FolderNotEmptyException;
import ua.vixdev.gym.exceptions.IOOperationException;
import ua.vixdev.gym.mapper.FolderTypesMapper;
import ua.vixdev.gym.repository.FolderTypesRepository;
import ua.vixdev.gym.service.implementation.FolderTypesServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class FolderTypesServiceTest {

    @InjectMocks
    FolderTypesServiceImpl folderService;

    @Mock
    FolderTypesMapper folderMapper;
    @Mock
    FolderTypesRepository folderRepository;

    FolderTypes folderTypes;
    FolderDto folderDto;
    CreateFolderDTO createFolderDTO;

    @BeforeEach
    void config() {
        folderTypes = new FolderTypes(1L, "temp_storage", true,
                LocalDateTime.of(2024, 3, 9, 10, 25, 31), null, List.of());
        folderDto = new FolderDto(1L, "temp_storage", LocalDateTime.of(2024, 3, 9, 10, 25, 31), null);

        createFolderDTO = new CreateFolderDTO();
        createFolderDTO.setTitle("temp_storage");
    }

    @Test
    void obtainFolderById() throws Exception {
        when(folderRepository.findByIdAndVisibleIsTrue(1L)).thenReturn(Optional.of(folderTypes));
        when(folderMapper.mapEntityToDto(folderTypes)).thenReturn(folderDto);

        FolderDto returnedFolder = folderService.obtainCertainFolder(1L);

        assertEquals(folderDto.getId(), returnedFolder.getId());
        assertEquals(folderDto.getTitle(), returnedFolder.getTitle());
        assertEquals(folderDto.getCreatedAt(), returnedFolder.getCreatedAt());
        assertEquals(folderDto.getUpdatedAt(), returnedFolder.getUpdatedAt());

        verify(folderRepository, times(1)).findByIdAndVisibleIsTrue(1L);
        verify(folderMapper, times(1)).mapEntityToDto(folderTypes);
    }

    @Test
    void obtainFolderById_EntityNotFoundException() {

        when(folderRepository.findByIdAndVisibleIsTrue(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> folderService.obtainCertainFolder(1L));
        assertEquals("Failed to obtain information about folder with id 1", e.getMessage());
    }

    @Test
    void getAllVisibleFolders() {
        when(folderRepository.getAllByVisibleIsTrue()).thenReturn(List.of(folderTypes));
        when(folderMapper.mapEntityToDto(folderTypes)).thenReturn(folderDto);

        List<FolderDto> visibleFolders = folderService.getAllVisibleFolders();

        assertEquals(1, visibleFolders.size());
        assertEquals(folderDto.getId(), visibleFolders.get(0).getId());
        assertEquals(folderDto.getTitle(), visibleFolders.get(0).getTitle());
        assertEquals(folderDto.getCreatedAt(), visibleFolders.get(0).getCreatedAt());
        assertEquals(folderDto.getUpdatedAt(), visibleFolders.get(0).getUpdatedAt());
    }

    @Test
    void createNewFolder() throws FolderAlreadyExistsException {
        when(folderRepository.save(folderTypes)).thenReturn(folderTypes);
        when(folderMapper.mapDtoToEntity(createFolderDTO)).thenReturn(folderTypes);

        Long saveFolderId = folderService.createNewFolder(createFolderDTO);

        assertEquals(1L, saveFolderId);
    }

    @Test
    void createNewFolder_FolderAlreadyExistsException() {
        Exception e = assertThrows(FolderAlreadyExistsException.class, () -> folderService.createNewFolder(createFolderDTO));

        assertEquals(e.getMessage(), "Folder with the name %s is already exists.".formatted(createFolderDTO.getTitle()));
    }

    @Test
    void changeVisibilityLeve() throws EntityNotFoundException {
        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));
        when(folderRepository.save(folderTypes)).thenReturn(folderTypes);

        folderService.changeFolderVisibility(1L, true);

        verify(folderRepository, times(1)).save(folderTypes);
    }

    @Test
    void changeVisibilityLevel_EntityNotFoundException() {
        when(folderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> folderService.changeFolderVisibility(1L, true));

        assertEquals(e.getMessage(), "Failed to get information about folder with id 1");
        verify(folderRepository, times(1)).findById(1L);
    }

    @Test
    void deleteFolder() throws EntityNotFoundException, FolderNotEmptyException, IOOperationException {
        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));

        folderService.deleteFolder(1L);

        verify(folderRepository, times(1)).findById(1L);
    }

    @Test
    void deleteFolder_EntityNotFoundException() {
        when(folderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> folderService.deleteFolder(1L));

        assertEquals(e.getMessage(), "Failed to found folder with id 1");
        verify(folderRepository, times(1)).findById(1L);
    }

    @Test
    void deleteFolder_FolderNotEmptyException() {
        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));

        folderTypes.setFiles(List.of(new Files(1L, "file", true, LocalDateTime.now(), null, folderTypes)));

        Exception e = assertThrows(FolderNotEmptyException.class, () -> folderService.deleteFolder(1L));

        assertEquals(e.getMessage(), "Failed to delete folder, because there are some files on it.");
    }

    @Test
    void deleteFolder_IOOperationException() {
        folderTypes.setTitle("random_folder/\\//"); // setting incorrect folder name to provoke IOException throwing
        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));

        Exception e = assertThrows(IOOperationException.class, () -> folderService.deleteFolder(1L));

        assertEquals(e.getMessage(), "Failed to delete folder. Please try again later.");
        verify(folderRepository, times(1)).findById(1L);
    }
}
