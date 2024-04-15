package ua.vixdev.gym.user.service;

import ua.vixdev.gym.user.dto.GenderDto;
import ua.vixdev.gym.user.dto.TypeDto;
import ua.vixdev.gym.user.entity.TypeEntity;

public interface TypeService {

    TypeEntity createNewType (TypeDto typeDto);
    TypeEntity findTypeById(Long id);
    TypeEntity updateType(Long id, TypeDto typeDto);
    void deleteTypeById(Long id);
}
