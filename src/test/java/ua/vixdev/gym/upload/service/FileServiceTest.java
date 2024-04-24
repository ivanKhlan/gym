package ua.vixdev.gym.upload.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import ua.vixdev.gym.upload.dto.FileInfoDTO;
import ua.vixdev.gym.upload.dto.FileRepresentationDTO;
import ua.vixdev.gym.upload.entity.FolderTypesEntity;
import ua.vixdev.gym.upload.entity.UploadFilesEntity;
import ua.vixdev.gym.upload.exceptions.EmptyFileException;
import ua.vixdev.gym.upload.exceptions.EntityNotFoundException;
import ua.vixdev.gym.upload.exceptions.IOOperationException;
import ua.vixdev.gym.upload.mapper.FileMapper;
import ua.vixdev.gym.upload.repository.FilesRepository;
import ua.vixdev.gym.upload.repository.FolderTypesRepository;
import ua.vixdev.gym.upload.service.implementation.FileServiceImpl;
import ua.vixdev.gym.upload.utils.HashingUtil;
import ua.vixdev.gym.upload.validation.FileValidation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileServiceTest {

    @InjectMocks
    FileServiceImpl fileService;

    @Mock
    FileValidation validation;
    @Mock
    FileMapper fileMapper;
    @Mock
    FilesRepository filesRepository;
    @Mock
    FolderTypesRepository folderTypesRepository;

    static String folderName = "temp_storage/";
    static String alreadySavedFile = "img.jpg";
    static String fileNameToSave = "img_2.png";
    static MockMultipartFile savedFile;
    static MockMultipartFile fileToSave;

    UploadFilesEntity files;
    FolderTypesEntity folder;
    FileRepresentationDTO fileRepresentation;

    @BeforeAll
    static void createFolderWithFile() throws IOException {
        savedFile = new MockMultipartFile("file", alreadySavedFile, MediaType.MULTIPART_FORM_DATA.toString(), "Text__".getBytes());

        File baseFolder =new File(folderName);
        baseFolder.mkdir();
        savedFile.transferTo(new File(folderName + savedFile.getOriginalFilename()));
    }

    @BeforeEach
    void setup() throws IOException {
        folder = new FolderTypesEntity(1L, folderName, true, LocalDateTime.of(2024, 9, 3, 12, 30, 10), null, null);
        files = new UploadFilesEntity(1L, alreadySavedFile, true, LocalDateTime.of(2024, 9, 3, 12, 30, 15), null, folder);

        fileRepresentation = new FileRepresentationDTO(1L, files.getName(), folder.getTitle(),
                files.getCreatedAt(), null, Arrays.toString(savedFile.getBytes()));

        fileToSave = new MockMultipartFile("file_to_save", fileNameToSave, MediaType.MULTIPART_FORM_DATA.toString(), "Text__".getBytes());
    }

    @AfterAll
    static void deleteFolder() throws IOException {
        java.nio.file.Files.delete(Path.of(folderName + alreadySavedFile));
        java.nio.file.Files.delete(Path.of(folderName));
    }

    @Test
    @Order(1)
    void getFileById() throws IOException {
        when(filesRepository.findByIdAndVisibleIsTrue(1L)).thenReturn(Optional.of(files));
        when(fileMapper.mapFileEntityToDTO(files)).thenReturn(fileRepresentation);

        FileRepresentationDTO obtainedFile = fileService.findFileById(1L);

        assertEquals(fileRepresentation.id(), obtainedFile.id());
        assertEquals(fileRepresentation.fileName(), obtainedFile.fileName());
        assertEquals(fileRepresentation.filePath(), obtainedFile.filePath());
        assertEquals(fileRepresentation.createdAt(), obtainedFile.createdAt());
        assertEquals(fileRepresentation.updatedAt(), obtainedFile.updatedAt());
        assertEquals(fileRepresentation.byteRepresentation(), obtainedFile.byteRepresentation());
    }

    @Test
    @Order(2)
    void getFileById_EntityNotFoundException() {
        when(filesRepository.findByIdAndVisibleIsTrue(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> fileService.findFileById(1L));

        assertEquals("Failed to find file with id 1", e.getMessage());
    }

    @Test
    @Order(3)
    void getFileById_IOOperationException() throws IOException {
        when(filesRepository.findByIdAndVisibleIsTrue(1L)).thenReturn(Optional.of(files));
        when(fileMapper.mapFileEntityToDTO(files)).thenThrow(new IOOperationException("Failed to get image information. Please try again later."));

        Exception e = assertThrows(IOOperationException.class, () -> fileService.findFileById(1L));

        assertEquals("Failed to get image information. Please try again later.", e.getMessage());
    }

    @Test
    @Order(4)
    void getAllVisibleFilesInFolder() throws IOException {
        folder.setFiles(List.of(files));
        when(folderTypesRepository.findById(1L)).thenReturn(Optional.of(folder));
        when(fileMapper.mapFileEntityToDTO(files)).thenReturn(fileRepresentation);

        List<FileRepresentationDTO> representations = fileService.findAllFilesInFolder(1L);

        assertEquals(1, representations.size());
        assertEquals(fileRepresentation.id(), representations.get(0).id());
        assertEquals(fileRepresentation.fileName(), representations.get(0).fileName());
        assertEquals(fileRepresentation.filePath(), representations.get(0).filePath());
        assertEquals(fileRepresentation.createdAt(), representations.get(0).createdAt());
        assertEquals(fileRepresentation.updatedAt(), representations.get(0).updatedAt());
        assertEquals(fileRepresentation.byteRepresentation(), representations.get(0).byteRepresentation());
    }

    @Test
    @Order(5)
    void getAllVisibleFilesInFolder_IOOperationException() throws IOException {
        folder.setFiles(List.of(files));

        when(folderTypesRepository.findById(1L)).thenReturn(Optional.of(folder));
        when(fileMapper.mapFileEntityToDTO(files)).thenThrow(new IOOperationException("Failed to get file bytes"));

        Exception e = assertThrows(IOOperationException.class, () -> fileService.findAllFilesInFolder(1L));

        assertEquals("Failed to get file bytes", e.getMessage());
    }

    @Test
    @Order(6)
    void getAllVisibleFilesInFolder_EntityNotFoundException() {
        when(folderTypesRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> fileService.findAllFilesInFolder(1L));

        assertEquals("Folder with id 1 doesn't exist", e.getMessage());
    }

    @Test
    @Order(7)
    void uploadFile() {
        String hashedName = "1b2c23iu2jnk";
        files.setName(fileToSave.getOriginalFilename());

        String[] splitArr = fileToSave.getOriginalFilename().split("\\.");
        try (MockedStatic<HashingUtil> utilities = Mockito.mockStatic(HashingUtil.class)) {
            when(folderTypesRepository.findById(1L)).thenReturn(Optional.of(folder));

            utilities.when(HashingUtil::createHashedFileName).thenReturn(hashedName);
            when(fileMapper.mapMultipartFileToEntity( hashedName + "." + splitArr[splitArr.length - 1],folder)).thenReturn(files);
            when(filesRepository.save(files)).thenReturn(files);

            FileInfoDTO representation = fileService.uploadFile(1L, fileToSave);

            assertEquals(hashedName + "." + splitArr[splitArr.length-1], representation.fileName());
            assertEquals(folder.getTitle() + files.getName(), representation.absPath());
            assertEquals(fileToSave.getContentType(), representation.contentType());
            assertEquals(fileToSave.getSize() + "B", representation.fileSize());
        }
    }

    @Test
    @Order(8)
    void uploadFile_EmptyFileException() throws EmptyFileException {
        String errMessage = "Uploaded file should not be empty.";
        doThrow(new EmptyFileException(errMessage)).when(validation).validateFile(fileToSave);

        Exception e = assertThrows(EmptyFileException.class, () -> fileService.uploadFile(1L, fileToSave));

        assertEquals(errMessage, e.getMessage());
    }

    @Test
    @Order(9)
    void uploadFile_EntityNotFoundException() {

        when(folderTypesRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> fileService.uploadFile(1L, fileToSave));

        assertEquals("Folder with id 1 does not exist.", e.getMessage());
    }

    @Test
    @Order(10)
    void uploadFile_IOOperationException() throws EmptyFileException {
        String hashedName = "217389q1p3n";
        folder.setTitle("_/"); // setting invalid folder name to provoke IOException throwing

        files.setName(fileToSave.getOriginalFilename());

        String[] splitArr = fileToSave.getOriginalFilename().split("\\.");
        try (MockedStatic<HashingUtil> utilities = Mockito.mockStatic(HashingUtil.class)) {
            doNothing().when(validation).validateFile(fileToSave);
            when(folderTypesRepository.findById(1L)).thenReturn(Optional.of(folder));

            utilities.when(HashingUtil::createHashedFileName).thenReturn(hashedName);
            when(fileMapper.mapMultipartFileToEntity(hashedName + "." + splitArr[splitArr.length - 1], folder)).thenReturn(files);
            when(filesRepository.save(files)).thenReturn(files);

            Exception e = assertThrows(IOOperationException.class, () -> fileService.uploadFile(1L, fileToSave));

            assertEquals("Something went wrong while uploading your file. Please try again later.", e.getMessage());
        }
    }

    @Test
    @Order(11)
    void updateFileVisibility() throws EntityNotFoundException {
        when(filesRepository.findById(1L)).thenReturn(Optional.of(files));
        when(filesRepository.save(files)).thenReturn(files);

        fileService.updateFileVisibility(1L, false);

        verify(filesRepository, times(1)).save(files);
    }

    @Test
    @Order(12)
    void updateFileVisibility_EntityNotFoundException() {
        when(filesRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> fileService.updateFileVisibility(1L, false));

        assertEquals("Failed to find file with id 1", e.getMessage());
    }

    @Test
    @Order(13)
    void deleteFile() throws EntityNotFoundException, IOOperationException {
        files.setName(fileNameToSave);
        when(filesRepository.findById(1L)).thenReturn(Optional.of(files));

        fileService.deleteFile(1L);

        verify(filesRepository, times(1)).findById(1L);
    }

    @Test
    @Order(14)
    void deleteFile_EntityNotFoundException() {
        when(filesRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> fileService.deleteFile(1L));

        assertEquals("Failed to find file with id 1", e.getMessage());
    }

    @Test
    @Order(15)
    void deleteFile_IOOperationException() {
        when(filesRepository.findById(1L)).thenReturn(Optional.of(files));
        folder.setTitle("_/"); //setting invalid folder name to provoke IOException throwing

        Exception e = assertThrows(IOOperationException.class, () -> fileService.deleteFile(1L));

        assertEquals("Failed to delete file. Please try again later.", e.getMessage());
    }

    @Test
    @Order(16)
    void updateFile() throws EmptyFileException, EntityNotFoundException, IOException {
        MockMultipartFile newFile = new MockMultipartFile("file", "img_3.png", MediaType.MULTIPART_FORM_DATA.toString(), "Image".getBytes());

        String hashedName = "UY2EOIPMLASDA";
        files.setName(newFile.getOriginalFilename());

        String[] splitArr = newFile.getOriginalFilename().split("\\.");
        try (MockedStatic<HashingUtil> utilities = Mockito.mockStatic(HashingUtil.class)) {
            when(folderTypesRepository.findById(1L)).thenReturn(Optional.of(folder));
            when(filesRepository.findById(1L)).thenReturn(Optional.of(files));

            utilities.when(HashingUtil::createHashedFileName).thenReturn(hashedName);
            when(fileMapper.mapMultipartFileToEntity( hashedName + "." + splitArr[splitArr.length - 1],folder)).thenReturn(files);
            when(filesRepository.save(files)).thenReturn(files);

            FileInfoDTO representation = fileService.updateFile(1L, newFile);

            assertEquals(hashedName + "." + splitArr[splitArr.length-1], representation.fileName());
            assertEquals(folder.getTitle() + files.getName(), representation.absPath());
            assertEquals(newFile.getContentType(), representation.contentType());
            assertEquals(newFile.getSize() + "B", representation.fileSize());
        }
    }

    @Test
    @Order(17)
    void updateFIle_EntityNotFoundException() {
        MockMultipartFile newFile = new MockMultipartFile("file", "img_3.png", MediaType.MULTIPART_FORM_DATA.toString(), "Image".getBytes());

        when(filesRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> fileService.updateFile(1L, newFile));

        assertEquals("Failed to find file with id 1", e.getMessage());
    }
}
