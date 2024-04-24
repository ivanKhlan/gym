package ua.vixdev.gym.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.dto.RequestTypeDto;
import ua.vixdev.gym.dto.ResponseTypeDto;
import ua.vixdev.gym.entity.TypeEntity;
import ua.vixdev.gym.factory.ResponseTypeDtoFactory;
import ua.vixdev.gym.service.TypeService;
import ua.vixdev.gym.utils.TypeValidationHelper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 *  Controller for managing types
 */
@RestController
@RequestMapping("/type/")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    private final ResponseTypeDtoFactory responseTypeDtoFactory;

    private final TypeValidationHelper validationHelper;

    private final static String GET_TYPE_BY_ID = "{id}/";
    private final static String UPDATE_TYPE_BY_ID = "{id}/";
    private final static String DELETE_TYPE_BY_ID = "{id}/";

    /**
     * Retrieves all available types
     * @return List of types
     */
    @GetMapping
    public ResponseEntity<List<ResponseTypeDto>> getAllTypes() {
        List<TypeEntity> typeEntities = typeService.getAllTypes();

        List<ResponseTypeDto> response = typeEntities
                .stream()
                .map(responseTypeDtoFactory::makeResponseTypeDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    /**
     * finds type by id and returns it
     * @param id Represents id of type
     * @return Found type
     */
    @GetMapping(GET_TYPE_BY_ID)
    public ResponseEntity<ResponseTypeDto> getTypeById(@PathVariable Long id) {
        Optional<TypeEntity> typeEntity = typeService.getTypeById(id);

        validationHelper.checkExistenceTypeEntity(typeEntity, id);

        TypeEntity existedTypeEntity = typeEntity.get();

        ResponseTypeDto response = responseTypeDtoFactory.makeResponseTypeDto(existedTypeEntity);

        return ResponseEntity.ok(response);
    }

    /**
     * creates type by given data
     * @param requestTypeDto Represents type that will be saved
     * @return String of confirmation
     */
    @PostMapping
    public ResponseEntity<String> createTypeEntity(@RequestBody RequestTypeDto requestTypeDto) {

        validationHelper.checkTypeValueLength(requestTypeDto);

        TypeEntity typeEntity = new TypeEntity(
                requestTypeDto.getValue(),
                requestTypeDto.getVisible(),
                requestTypeDto.getCreatedAt(),
                requestTypeDto.getUpdatedAt(),
                requestTypeDto.getDeletedAt()
        );

        TypeEntity createdTypeEntity = typeService.saveTypeEntity(typeEntity);

        return ResponseEntity.ok("type with id: %d was created".formatted(createdTypeEntity.getId()));
    }

    /**
     * alters existed type
     * @param id Represents id of type
     * @param requestTypeDto Represents type that will be saved
     * @return String of confirmation
     */
    @PutMapping(UPDATE_TYPE_BY_ID)
    public ResponseEntity<String> updateType(@PathVariable Long id, @RequestBody RequestTypeDto requestTypeDto) {
        Optional<TypeEntity> typeEntity = typeService.getTypeById(id);

        validationHelper.checkExistenceTypeEntity(typeEntity, id);
        validationHelper.checkTypeValueLength(requestTypeDto);
        validationHelper.validateCreatedAtValue(requestTypeDto, typeEntity.get());

        TypeEntity foundTypeEntity = new TypeEntity(
                id,
                requestTypeDto.getValue(),
                requestTypeDto.getVisible(),
                requestTypeDto.getCreatedAt(),
                Instant.now(),
                requestTypeDto.getDeletedAt()
        );

        typeService.saveTypeEntity(foundTypeEntity);

        return ResponseEntity.ok("type entity with id: %d was altered".formatted(id));
    }

    /**
     * deletes existed type
     * @param id Represents id of type
     * @return String of confirmation
     */
    @DeleteMapping(DELETE_TYPE_BY_ID)
    public ResponseEntity<String> deleteTypeById(@PathVariable Long id) {
        Optional<TypeEntity> typeEntity = typeService.getTypeById(id);

        validationHelper.checkExistenceTypeEntity(typeEntity, id);

        TypeEntity existedTypeEntity = typeEntity.get();
        existedTypeEntity.setVisible(false);
        existedTypeEntity.setDeletedAt(Instant.now());


        typeService.saveTypeEntity(existedTypeEntity);

        return ResponseEntity.ok("type with id: %d was deleted".formatted(id));
    }
}
