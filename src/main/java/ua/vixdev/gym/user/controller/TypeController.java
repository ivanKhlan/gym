package ua.vixdev.gym.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.user.dto.TypeDto;
import ua.vixdev.gym.user.entity.TypeEntity;
import ua.vixdev.gym.user.service.TypeService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/types")
public class TypeController {
    private final TypeService typeService;

    @PostMapping()
    ResponseEntity<?> createNewType(@RequestBody @Valid TypeDto typeDto) {
        var type = typeService.createNewType(typeDto);
        return new ResponseEntity<>(type, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TypeEntity findTypeById(@PathVariable Long id) {
        return typeService.findTypeById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateType(@PathVariable Long id, @RequestBody @Valid TypeDto typeDto) {
        var type = typeService.updateType(id, typeDto);
        return new ResponseEntity<>(type, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTypeById(@PathVariable Long id) {
        typeService.deleteTypeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
