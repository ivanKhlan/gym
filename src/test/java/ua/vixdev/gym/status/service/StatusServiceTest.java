package ua.vixdev.gym.status.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.vixdev.gym.status.dto.StatusDto;
import ua.vixdev.gym.status.entity.Status;
import ua.vixdev.gym.status.entity.StatusDtoData;
import ua.vixdev.gym.status.entity.StatusEntityData;
import ua.vixdev.gym.status.exceptions.StatusNotFoundException;
import ua.vixdev.gym.status.repository.StatusRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {
    @Mock
    StatusRepository statusRepository;

    @InjectMocks
    StatusServiceImpl statusService;

    //list status
    @Test
    void should_return_all_statuses() {
        List<Status> statuses = StatusEntityData.getListOfStatus();

        when(statusRepository.findAll()).thenReturn(statuses);
        List<Status> expected = statusService.findAllStatuses();

        //then
        assertEquals(expected, statuses);
        verify(statusRepository).findAll();

    }
    //detail status
    @Test
    void when_given_id_should_return_status_if_found(){
        //given
        Status status = StatusEntityData.getSingleStatusWithId();

        //when
        when(statusRepository.findById(status.getId())).thenReturn(Optional.of(status));
        statusService.deleteStatusById(status.getId());

        //then
        verify(statusRepository).deleteById(status.getId());
    }
    @Test()
    void should_throw_exception_when_status_doesnt_exist() {
        //given
        Status status = StatusEntityData.getSingleStatusWithId();

        //when
        when(statusRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        StatusNotFoundException exception= assertThrows(StatusNotFoundException.class, () ->
                statusService.deleteStatusById(status.getId()));

        //then
        assertTrue(exception.getMessage().contains("Could not find status with id {1}!"));
    }

    //create status
    @Test
    void when_save_status_should_return_status() {

        //given
        StatusDto statusDto = StatusDtoData.getSingleStatusDto();
        Status status = StatusEntityData.getSingleStatus();

        //when
        when(statusRepository.save(any(Status.class))).thenReturn(status);
        Status created = statusService.createStatus(statusDto);

        //then
        assertThat(created.getValue()).isSameAs(statusDto.getValue());
    }

    //update user
    @Test
    void when_given_id_should_update_status_if_found() {
        //given
        Status status = StatusEntityData.getSingleStatusWithId();
        StatusDto updateStatus = StatusDtoData.getUpdatedStatusDto();
        Status status1 = updateStatus.fromDto();
        status1.setId(1L);

        //when
        when(statusRepository.findById(status.getId())).thenReturn(Optional.of(status));
        statusService.updateStatus(status.getId(), updateStatus);


        //then
        verify(statusRepository).findById(status.getId());
    }

    @Test()
    void should_throw_exception_when_update_status_doesnt_exist() {
        //given
        Status status = StatusEntityData.getSingleStatusWithId();

        //when
        when(statusRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        StatusNotFoundException exception = assertThrows(StatusNotFoundException.class, () ->
                statusService.deleteStatusById(status.getId()));

        //then
        assertTrue(exception.getMessage().contains("Could not find status with id {1}!"));
    }

    //delete user
    @Test
    void when_given_id_should_delete_status_if_found(){
        //given
        Status status = StatusEntityData.getSingleStatusWithId();

        //when
        when(statusRepository.findById(status.getId())).thenReturn(Optional.of(status));
        statusService.deleteStatusById(status.getId());

        //then
        verify(statusRepository).deleteById(status.getId());
    }
    @Test()
    void should_throw_exception_when_delete_status_doesnt_exist() {
        //given
        Status status = StatusEntityData.getSingleStatusWithId();

        //when
        when(statusRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        StatusNotFoundException exception = assertThrows(StatusNotFoundException.class, () ->
                statusService.deleteStatusById(status.getId()));

        //then
        assertTrue(exception.getMessage().contains("Could not find status with id {1}!"));
    }

}
