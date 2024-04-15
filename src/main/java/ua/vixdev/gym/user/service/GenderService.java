package ua.vixdev.gym.user.service;

import ua.vixdev.gym.user.dto.GenderDto;
import ua.vixdev.gym.user.entity.GenderEntity;

public interface GenderService {

    GenderEntity createNewGender(GenderDto genderDto);
    GenderEntity findGenderById(Long id);
    GenderEntity updateGender(Long id, GenderDto genderDto);
    void deleteGenderById(Long id);
}
