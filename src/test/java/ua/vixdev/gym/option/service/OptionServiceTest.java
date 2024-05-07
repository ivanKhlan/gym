package ua.vixdev.gym.option.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.vixdev.gym.option.entity.OptionDtoData;
import ua.vixdev.gym.option.entity.OptionEntityData;
import ua.vixdev.gym.options.dto.OptionDto;
import ua.vixdev.gym.options.entity.OptionEntity;
import ua.vixdev.gym.options.exceptions.OptionNotFoundException;
import ua.vixdev.gym.options.repository.OptionsRepository;
import ua.vixdev.gym.options.service.OptionService;
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
    OptionService optionService;

    //list status
    @Test
    void should_return_all_options() {
        List<OptionEntity> options = OptionEntityData.getListOfOption();

        when(optionsRepository.findAll()).thenReturn(options);
        List<OptionEntity> expected = optionService.findAllOptions();

        //then
        assertEquals(expected, options);
        verify(optionsRepository).findAll();

    }

    //detail status
    @Test
    void when_given_id_should_return_option_if_found() {
        //given
        OptionEntity optionEntity = OptionEntityData.getSingleOptionWithId();

        //when
        when(optionsRepository.findById(optionEntity.getId())).thenReturn(Optional.of(optionEntity));
        optionService.deleteOptionById(optionEntity.getId());

        //then
        verify(optionsRepository).deleteById(optionEntity.getId());
    }

    @Test()
    void should_throw_exception_when_option_doesnt_exist() {
        //given
        OptionEntity optionEntity = OptionEntityData.getSingleOptionWithId();

        //when
        when(optionsRepository.findById(anyLong())).thenReturn(Optional.empty());
        OptionNotFoundException exception = assertThrows(OptionNotFoundException.class, () ->
                optionService.deleteOptionById(optionEntity.getId()));

        //then
        assertTrue(exception.getMessage().contains("Could not find option with id {1}!"));
    }

    //create status
    @Test
    void when_save_option_should_return_option() {

        //given
        OptionDto optionDto = OptionDtoData.getSingleOptionDto();
        OptionEntity option = OptionEntityData.getSingleOption();

        //when
        when(optionsRepository.save(any(OptionEntity.class))).thenReturn(option);
        OptionEntity created = optionService.createOption(optionDto);

        //then
        assertThat(created.getValue()).isSameAs(optionDto.getValue());
    }

    //update user
    @Test
    void when_given_id_should_update_option_if_found() {
        //given
        OptionEntity option = OptionEntityData.getSingleOptionWithId();
        OptionDto updateOption = OptionDtoData.getUpdatedOptionDto();
        OptionEntity option1 = updateOption.fromDto();
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
        OptionEntity option = OptionEntityData.getSingleOptionWithId();

        //when
        when(optionsRepository.findById(anyLong())).thenReturn(Optional.empty());
        OptionNotFoundException exception = assertThrows(OptionNotFoundException.class, () ->
                optionService.deleteOptionById(option.getId()));

        //then
        assertTrue(exception.getMessage().contains("Could not find option with id {1}!"));
    }

    //delete user
    @Test
    void when_given_id_should_delete_option_if_found() {
        //given
        OptionEntity option = OptionEntityData.getSingleOptionWithId();

        //when
        when(optionsRepository.findById(option.getId())).thenReturn(Optional.of(option));
        optionService.deleteOptionById(option.getId());

        //then
        verify(optionsRepository).deleteById(option.getId());
    }

    @Test()
    void should_throw_exception_when_delete_option_doesnt_exist() {
        //given
       OptionEntity option = OptionEntityData.getSingleOptionWithId();

        //when
        when(optionsRepository.findById(anyLong())).thenReturn(Optional.empty());
        OptionNotFoundException exception = assertThrows(OptionNotFoundException.class, () ->
                optionService.deleteOptionById(option.getId()));

        //then
        assertTrue(exception.getMessage().contains("Could not find option with id {1}!"));
    }
}
