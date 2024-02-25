package ua.vixdev.gym.option.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.vixdev.gym.option.entity.OptionDtoData;
import ua.vixdev.gym.option.entity.OptionEntityData;
import ua.vixdev.gym.options.dto.OptionDto;
import ua.vixdev.gym.options.entity.Options;
import ua.vixdev.gym.options.exceptions.OptionNotFoundException;
import ua.vixdev.gym.options.repository.OptionsRepository;
import ua.vixdev.gym.options.service.OptionServiceImpl;
import ua.vixdev.gym.status.dto.StatusDto;
import ua.vixdev.gym.status.entity.Status;
import ua.vixdev.gym.status.entity.StatusDtoData;
import ua.vixdev.gym.status.entity.StatusEntityData;
import ua.vixdev.gym.status.exceptions.StatusNotFoundException;
import ua.vixdev.gym.status.repository.StatusRepository;
import ua.vixdev.gym.status.service.StatusServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OptionServiceTest {

    @Mock
    OptionsRepository optionsRepository;

    @InjectMocks
    OptionServiceImpl optionService;

    //list status
    @Test
    void should_return_all_options() {
        List<Options> options = OptionEntityData.getListOfOption();

        when(optionsRepository.findAll()).thenReturn(options);
        List<Options> expected = optionService.findAllOptions();

        //then
        assertEquals(expected, options);
        verify(optionsRepository).findAll();

    }

    //detail status
    @Test
    void when_given_id_should_return_option_if_found() {
        //given
        Options options = OptionEntityData.getSingleOptionWithId();

        //when
        when(optionsRepository.findById(options.getId())).thenReturn(Optional.of(options));
        optionService.deleteOptionById(options.getId());

        //then
        verify(optionsRepository).deleteById(options.getId());
    }

    @Test()
    void should_throw_exception_when_option_doesnt_exist() {
        //given
        Options options = OptionEntityData.getSingleOptionWithId();

        //when
        when(optionsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        OptionNotFoundException exception = assertThrows(OptionNotFoundException.class, () ->
                optionService.deleteOptionById(options.getId()));

        //then
        assertTrue(exception.getMessage().contains("Could not find option with id {1}!"));
    }

    //create status
    @Test
    void when_save_option_should_return_option() {

        //given
        OptionDto optionDto = OptionDtoData.getSingleOptionDto();
        Options option = OptionEntityData.getSingleOption();

        //when
        when(optionsRepository.save(any(Options.class))).thenReturn(option);
        Options created = optionService.createOption(optionDto);

        //then
        assertThat(created.getValue()).isSameAs(optionDto.getValue());
    }

    //update user
    @Test
    void when_given_id_should_update_option_if_found() {
        //given
        Options option = OptionEntityData.getSingleOptionWithId();
        OptionDto updateOption = OptionDtoData.getUpdatedOptionDto();
        Options option1 = updateOption.fromDto();
        option1.setId(1L);

        //when
        when(optionsRepository.findById(option.getId())).thenReturn(Optional.of(option));
        optionService.updateOption(option.getId(), updateOption);


        //then
        verify(optionsRepository).findById(option.getId());
    }

    @Test()
    void should_throw_exception_when_update_option_doesnt_exist() {
        //given
        Options option = OptionEntityData.getSingleOptionWithId();

        //when
        when(optionsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        OptionNotFoundException exception = assertThrows(OptionNotFoundException.class, () ->
                optionService.deleteOptionById(option.getId()));

        //then
        assertTrue(exception.getMessage().contains("Could not find option with id {1}!"));
    }

    //delete user
    @Test
    void when_given_id_should_delete_option_if_found() {
        //given
        Options option = OptionEntityData.getSingleOptionWithId();

        //when
        when(optionsRepository.findById(option.getId())).thenReturn(Optional.of(option));
        optionService.deleteOptionById(option.getId());

        //then
        verify(optionsRepository).deleteById(option.getId());
    }

    @Test()
    void should_throw_exception_when_delete_option_doesnt_exist() {
        //given
       Options option = OptionEntityData.getSingleOptionWithId();

        //when
        when(optionsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        OptionNotFoundException exception = assertThrows(OptionNotFoundException.class, () ->
                optionService.deleteOptionById(option.getId()));

        //then
        assertTrue(exception.getMessage().contains("Could not find option with id {1}!"));
    }
}
