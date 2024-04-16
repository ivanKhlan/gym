package ua.vixdev.gym.upload.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.vixdev.gym.upload.dto.CreateFolderDTO;
import ua.vixdev.gym.upload.dto.FolderDto;
import ua.vixdev.gym.upload.entity.UploadFilesEntity;
import ua.vixdev.gym.upload.entity.FolderTypesEntity;
import ua.vixdev.gym.upload.exceptions.*;
import ua.vixdev.gym.upload.mapper.FolderTypesMapper;
import ua.vixdev.gym.upload.repository.FolderTypesRepository;
import ua.vixdev.gym.upload.service.implementation.FolderTypesServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FolderTypesServiceTest {

    @InjectMocks
    FolderTypesServiceImpl folderService;

    @Mock
    FolderTypesMapper folderMapper;
    @Mock
    FolderTypesRepository folderRepository;

    FolderTypesEntity folderTypes;
    FolderDto folderDto;
    CreateFolderDTO createFolderDTO;
    String folderName = "temp_storage";

    @BeforeEach
    void config() {
        folderTypes = new FolderTypesEntity(1L, folderName, true,
                LocalDateTime.of(2024, 3, 9, 10, 25, 31), null, List.of());
        folderDto = new FolderDto(1L, folderName, LocalDateTime.of(2024, 3, 9, 10, 25, 31), null);

        createFolderDTO = new CreateFolderDTO();
        createFolderDTO.setTitle(folderName);
    }

    @Test
    @Order(1)
    void getFolderById() {
        when(folderRepository.findByIdAndVisibleIsTrue(1L)).thenReturn(Optional.of(folderTypes));
        when(folderMapper.mapEntityToDto(folderTypes)).thenReturn(folderDto);

        FolderDto returnedFolder = folderService.findFolderById(1L);

        assertEquals(folderDto.getId(), returnedFolder.getId());
        assertEquals(folderDto.getTitle(), returnedFolder.getTitle());
        assertEquals(folderDto.getCreatedAt(), returnedFolder.getCreatedAt());
        assertEquals(folderDto.getUpdatedAt(), returnedFolder.getUpdatedAt());

        verify(folderRepository, times(1)).findByIdAndVisibleIsTrue(1L);
        verify(folderMapper, times(1)).mapEntityToDto(folderTypes);
    }

    @Test
    @Order(2)
    void getFolderById_EntityNotFoundException() {

        when(folderRepository.findByIdAndVisibleIsTrue(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> folderService.findFolderById(1L));
        assertEquals("Failed to get information about folder with id 1", e.getMessage());
    }

    @Test
    @Order(3)
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
    @Order(4)
    void createNewFolder() {
        when(folderRepository.save(folderTypes)).thenReturn(folderTypes);
        when(folderMapper.mapDtoToEntity(createFolderDTO)).thenReturn(folderTypes);

        Long saveFolderId = folderService.createNewFolder(createFolderDTO);

        assertEquals(1L, saveFolderId);
    }

    @Test
    @Order(5)
    void createNewFolder_FolderAlreadyExistsException() {
        Exception e = assertThrows(FolderAlreadyExistsException.class, () -> folderService.createNewFolder(createFolderDTO));

        assertEquals(e.getMessage(), "Folder with the name %s is already exists.".formatted(createFolderDTO.getTitle()));
    }

    @Test
    @Order(6)
    void renameFolder() throws Exception {
        String folderToRename = "/data";
        String newFolderName = "temp_folder_renamed";

        createTempFolderBeforeRenaming(folderToRename); // creating folder inside already create to rename it

        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));
        folderService.renameFolder(1L, newFolderName);


        verify(folderRepository, times(1)).findById(1L);

        deleteTempFolderAfterRenaming(); // deleting nested folder created for this test
        folderTypes.setTitle(folderName); //setting folder name to the very beginning version
    }

    @Test
    @Order(7)
    void renameFolder_EntityNotFoundException() {
        when(folderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> folderService.renameFolder(1L, ""));

        assertEquals("Failed to find folder with id 1", e.getMessage());
    }

    @Test
    @Order(8)
    void renameFolder_FailedRenameFolderException() {
        String newFolderName = "some_name";
        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));

        // Exception will be thrown because .getParentFile() method will produce null
        Exception e = assertThrows(FailedRenameFolderException.class, () -> folderService.renameFolder(1L, newFolderName));

        assertEquals("Failed to rename folder from %s to %s. Most likely such a folder already exists.".formatted(folderName, "null\\"+newFolderName+"\\"),
                e.getMessage());
    }

    @Test
    @Order(9)
    void changeVisibilityLeve() {
        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));
        when(folderRepository.save(folderTypes)).thenReturn(folderTypes);

        folderService.changeFolderVisibility(1L, true);

        verify(folderRepository, times(1)).save(folderTypes);
    }

    @Test
    @Order(10)
    void changeVisibilityLevel_EntityNotFoundException() {
        when(folderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> folderService.changeFolderVisibility(1L, true));

        assertEquals(e.getMessage(), "Failed to find folder with id 1");
        verify(folderRepository, times(1)).findById(1L);
    }

    @Test
    @Order(11)
    void deleteFolder() {
        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));

        folderService.deleteFolder(1L);

        verify(folderRepository, times(1)).findById(1L);
    }

    @Test
    @Order(12)
    void deleteFolder_EntityNotFoundException() {
        when(folderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> folderService.deleteFolder(1L));

        assertEquals(e.getMessage(), "Failed to find folder with id 1");
        verify(folderRepository, times(1)).findById(1L);
    }

    @Test
    @Order(13)
    void deleteFolder_FolderNotEmptyException() {
        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));

        folderTypes.setFiles(List.of(new UploadFilesEntity(1L, "file", true, LocalDateTime.now(), null, folderTypes)));

        Exception e = assertThrows(FolderNotEmptyException.class, () -> folderService.deleteFolder(1L));

        assertEquals(e.getMessage(), "Failed to delete folder, because there are some files on it.");
    }

    @Test
    @Order(14)
    void deleteFolder_IOOperationException() {
        folderTypes.setTitle("random_folder/\\//"); // setting incorrect folder name to provoke IOException throwing
        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));

        Exception e = assertThrows(IOOperationException.class, () -> folderService.deleteFolder(1L));

        assertEquals(e.getMessage(), "Failed to delete folder. Please try again later.");
        verify(folderRepository, times(1)).findById(1L);
    }

    private void createTempFolderBeforeRenaming(String folderName) {
        createFolderDTO.setTitle(createFolderDTO.getTitle() + folderName);
        folderTypes.setTitle(folderTypes.getTitle() + folderName); // renaming folder name to represent nested one
        when(folderRepository.save(folderTypes)).thenReturn(folderTypes);
        when(folderMapper.mapDtoToEntity(createFolderDTO)).thenReturn(folderTypes);

        folderService.createNewFolder(createFolderDTO);
    }

    private void deleteTempFolderAfterRenaming() {
        when(folderRepository.findById(1L)).thenReturn(Optional.of(folderTypes));

        folderService.deleteFolder(1L);
    }
}
