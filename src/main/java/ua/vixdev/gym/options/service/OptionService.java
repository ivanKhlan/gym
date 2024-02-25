package ua.vixdev.gym.options.service;

import ua.vixdev.gym.options.dto.OptionDto;
import ua.vixdev.gym.options.entity.Options;

import java.util.List;

public interface OptionService {
Options findByKey(String key);
List<Options> findAllByValue(String value);
List<Options> findAllOptions();
List<Options> findOptionsByVisible(boolean visible);
Options findOptionById(Long id);
Options createOption(OptionDto optionDto);
Options updateOption(Long id, OptionDto updateOptionDto);
void deleteOptionById(Long id);
void updateOptionVisibility(Long id, String visible);

}
