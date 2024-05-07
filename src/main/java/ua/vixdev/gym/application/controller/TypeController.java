package ua.vixdev.gym.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.application.controller.dto.TypeDto;
import ua.vixdev.gym.application.entity.TypeEntity;
import ua.vixdev.gym.application.service.TypeService;

import java.util.List;

/**
 *  Controller for managing types
 */
@RestController
@RequestMapping("/types")
@RequiredArgsConstructor
public class TypeController {

    private static final Long EMPTY_ID = null;

    private final TypeService typeService;

    /**
     * Retrieves all available types
     * @return List of types
     */
    @GetMapping
    public List<TypeEntity> findAll() {
        return typeService.findAll();
    }

    /**
     * finds type by id and returns it
     * @param id Represents id of type
     * @return Found type
     */
    @GetMapping("/{id}")
    public TypeEntity findById(@PathVariable Long id) {
        return typeService.findById(id);
    }

    /**
     * creates type by given data
     * @param typeDto Represents type that will be saved
     * @return String of confirmation
     */
    @PostMapping
    public TypeEntity createType(@RequestBody @Valid TypeDto typeDto) {
        return typeService.createType(mapToType(EMPTY_ID, typeDto));
    }

    /**
     * alters existed type
     * @param id Represents id of type
     * @param typeDto Represents type that will be saved
     * @return String of confirmation
     */
    @PutMapping("/{id}")
    public TypeEntity updateType(@PathVariable Long id, @RequestBody @Valid TypeDto typeDto) {
        return typeService.updateType(mapToType(id, typeDto));
    }

    /**
     * deletes existed type
     * @param id Represents id of type
     * @return String of confirmation
     */
    @DeleteMapping("/{id}")
    @ResponseStatus()
    public void deleteTypeById(@PathVariable Long id) {
        typeService.deleteById(id);
    }

    public TypeEntity mapToType(Long id, TypeDto typeDto) {
        return TypeEntity.builder()
                .id(id)
                .value(typeDto.getValue())
                .visible(typeDto.getVisible())
                .build();
    }
}
