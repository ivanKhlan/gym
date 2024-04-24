package ua.vixdev.gym.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.vixdev.gym.dto.ApplicationDto;
import ua.vixdev.gym.entity.Application;
import ua.vixdev.gym.exception.ApplicationNotFoundException;
import ua.vixdev.gym.mapper.ApplicationMapper;
import ua.vixdev.gym.repository.ApplicationRepository;

/**
 * Unit tests for {@link ApplicationService} class.
 */
@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

  @Mock
  private ApplicationRepository applicationRepository;

  @Mock
  private ApplicationMapper applicationMapper;

  @InjectMocks
  private ApplicationService applicationService;

  private Application testEntity;
  private ApplicationDto testDto;

  @BeforeEach
  public void init() {
    int testApplicationId = 123;

    testEntity = Application.builder()
        .id(testApplicationId)
        .build();

    testDto = ApplicationDto.builder()
        .id(testApplicationId)
        .build();
  }

  @Test
  public void testGetAllApplications() {
    when(applicationRepository.findAllByDeletedAtIsNull()).thenReturn(List.of(testEntity));
    when(applicationMapper.toDto(any())).thenReturn(testDto);

    List<ApplicationDto> expected = List.of(testDto);
    List<ApplicationDto> result = applicationService.getAllApplications();

    assertEquals(expected, result);
  }

  @Test
  public void testCreateApplication() {
    when(applicationRepository.save(any())).thenReturn(testEntity);
    when(applicationMapper.toDto(any())).thenReturn(testDto);

    ApplicationDto resultApplication = applicationService.createApplication(any());
    assertEquals(testDto, resultApplication);
  }

  @Test
  public void givenApplicationId_whenGetBiId_thenThrowNotFoundException() {
    when(applicationRepository.findByIdAndDeletedAtIsNull(anyInt()))
        .thenReturn(Optional.empty());
    assertThrows(ApplicationNotFoundException.class, () -> {
      applicationService.getApplicationById(anyInt());
    });
  }

  @Test
  public void givenApplicationId_whenGetBiId_thenReturnFoundApplicationDto() {
    when(applicationRepository.findByIdAndDeletedAtIsNull(anyInt()))
        .thenReturn(Optional.of(testEntity));
    when(applicationMapper.toDto(any())).thenReturn(testDto);

    ApplicationDto resultApp = applicationService.getApplicationById(anyInt());
    assertEquals(resultApp, testDto);
  }

  @Test
  public void givenApplicationDto_whenUpdate_thenThrowNotFoundException() {
    when(applicationRepository.findByIdAndDeletedAtIsNull(anyInt()))
        .thenReturn(Optional.empty());
    assertThrows(ApplicationNotFoundException.class, () -> {
      applicationService.updateApplication(testDto);
    });
  }

  @Test
  public void givenApplicationDto_whenUpdate_thenReturnUpdatedDto() {
    when(applicationRepository.findByIdAndDeletedAtIsNull(anyInt()))
        .thenReturn(Optional.of(testEntity));
    doNothing().when(applicationMapper).updateFromDto(any(), any());
    when(applicationMapper.toDto(any())).thenReturn(testDto);

    ApplicationDto result = applicationService.updateApplication(testDto);
    assertEquals(result, testDto);
  }

  @Test
  public void givenApplicationId_whenDelete_thenThrowNotFoundException() {
    when(applicationRepository.findByIdAndDeletedAtIsNull(anyInt()))
        .thenReturn(Optional.empty());
    assertThrows(ApplicationNotFoundException.class, () -> {
      applicationService.deleteApplication(anyInt());
    });
  }

  @Test
  public void givenApplicationId_whenDelete_thenSetDeletedAt() {
    when(applicationRepository.findByIdAndDeletedAtIsNull(anyInt()))
        .thenReturn(Optional.of(testEntity));
    doNothing().when(applicationMapper).softDelete(any());
    when(applicationMapper.toDto(any())).thenReturn(testDto);

    ApplicationDto result = applicationService.deleteApplication(anyInt());
    assertEquals(result, testDto);
  }
}
