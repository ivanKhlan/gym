package ua.vixdev.gym.options.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.options.dto.OptionDto;
import ua.vixdev.gym.options.entity.OptionEntity;
import ua.vixdev.gym.options.service.OptionService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/options")
public class OptionController {

    private static final Long EMPTY_ID = null;
    private final OptionService optionService;

    @ResponseStatus(OK)
    @GetMapping()
    List<OptionEntity> findAllOptions() {
        return optionService.findAllOptions();
    }

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    OptionEntity findOptionsById(@PathVariable Long id) {
        return optionService.findOptionById(id);
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    OptionEntity createOption(@RequestBody @Valid OptionDto optionDto) {
        return optionService.createOption(mapToOption(EMPTY_ID, optionDto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    OptionEntity updateOption(@PathVariable Long id, @RequestBody @Valid OptionDto optionDto) {
        return optionService.updateOption(mapToOption(id, optionDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    void deleteOption(@PathVariable Long id) {
        optionService.deleteOptionById(id);
    }

    private OptionEntity mapToOption(Long id, OptionDto optionDto) {
        return OptionEntity.builder()
                .id(id)
                .value(optionDto.getValue())
                .key(optionDto.getKey())
                .autoload(optionDto.isAutoload())
                .visible(optionDto.isVisible())
                .build();
    }
}
