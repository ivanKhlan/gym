package ua.vixdev.gym.options.service;

import ua.vixdev.gym.options.dto.OptionDto;
import ua.vixdev.gym.options.entity.OptionEntity;

import java.util.List;

public interface OptionService {
OptionEntity findByKey(String key);
List<OptionEntity> findAllByValue(String value);
List<OptionEntity> findAllOptions();
List<OptionEntity> findOptionsByVisible(boolean visible);
OptionEntity findOptionById(Long id);
OptionEntity createOption(OptionDto optionDto);
OptionEntity updateOption(Long id, OptionDto updateOptionDto);
void deleteOptionById(Long id);
void updateOptionVisibility(Long id, String visible);

}
