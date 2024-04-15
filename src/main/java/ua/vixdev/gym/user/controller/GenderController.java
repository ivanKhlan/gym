package ua.vixdev.gym.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.user.dto.GenderDto;
import ua.vixdev.gym.user.entity.GenderEntity;
import ua.vixdev.gym.user.service.GenderService;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping(value = "/genders")
public class GenderController {
    private final GenderService genderService;


    @PostMapping()
    ResponseEntity<?> createGender(@RequestBody @Valid GenderDto genderDto) {
        var gender = genderService.createNewGender(genderDto);
        return new ResponseEntity<>(gender, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GenderEntity findGenderById(@PathVariable Long id) {
        return genderService.findGenderById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateGender(@PathVariable Long id, @RequestBody @Valid GenderDto genderDto) {
        var gender = genderService.updateGender(id, genderDto);
        return new ResponseEntity<>(gender, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteGenderById(@PathVariable Long id) {
        genderService.deleteGenderById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
