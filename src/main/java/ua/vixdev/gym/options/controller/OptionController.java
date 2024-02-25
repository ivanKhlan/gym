package ua.vixdev.gym.options.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.options.dto.OptionDto;
import ua.vixdev.gym.options.entity.Options;
import ua.vixdev.gym.options.exceptions.OptionVisibleException;
import ua.vixdev.gym.options.service.OptionService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/options")
public class OptionController {
    private final OptionService optionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    List<Options> getAllOptions(){
        return optionService.findAllOptions();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Options findUserById(@PathVariable Long id) {
        return optionService.findOptionById(id);
    }

    @PostMapping()
    ResponseEntity<?> createOption(@RequestBody @Valid OptionDto optionDto) {
        var option = optionService.createOption(optionDto);
        return new ResponseEntity<>(option, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateOption(@PathVariable Long id, @RequestBody @Valid OptionDto optionDto) {
        var option = optionService.updateOption(id, optionDto);
        return new ResponseEntity<>(option, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}/visible")
    ResponseEntity<?> updateOptionVisibility(@PathVariable Long id,
                                           @RequestBody Map<String, String> body) {

        var visible = body.get("visible");
        if (StringUtils.isNotBlank(visible)
                && (StringUtils.equalsIgnoreCase(visible, "true") ||
                StringUtils.equalsIgnoreCase(visible, "false"))) {
            visible = visible.toLowerCase();
            optionService.updateOptionVisibility(id, visible);
            return ResponseEntity.accepted().build();
        }
        log.error("Incorrect data was provided when update the option's visibility: {}!", visible);
        throw new OptionVisibleException(visible);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOption(@PathVariable Long id) {
        optionService.deleteOptionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
