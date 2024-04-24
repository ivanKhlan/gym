package ua.vixdev.gym.options.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.options.dto.OptionDto;
import ua.vixdev.gym.options.entity.OptionEntity;
import ua.vixdev.gym.options.exceptions.OptionAlreadyExists;
import ua.vixdev.gym.options.exceptions.OptionNotFoundException;
import ua.vixdev.gym.options.repository.OptionsRepository;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class OptionServiceImpl implements OptionService {
    private final OptionsRepository optionsRepository;

    @Override
    public OptionEntity findByKey(String key) {
        return optionsRepository.findByKey(key).orElse(null);
    }

    @Override
    public List<OptionEntity> findAllByValue(String value) {
        return printLogInfo(optionsRepository.findAllByValue(value));
    }

    @Override
    public List<OptionEntity> findAllOptions() {
        return printLogInfo(optionsRepository.findAll());
    }

    @Override
    public List<OptionEntity> findOptionsByVisible(boolean visible) {
        return printLogInfo(optionsRepository.findAllByVisible(visible));
    }

    @Override
    public OptionEntity findOptionById(Long id) {
        var loadOption = optionsRepository.findById(id);
        log.info("Loaded option with ID: {}", id);

        return loadOption.orElseThrow(() -> {
            log.error("Option with ID: {} not found!", id);
            return new OptionNotFoundException(id);
        });

    }

    @Transactional
    @Override
    public OptionEntity createOption(OptionDto optionDto) {
        var optionEntity = optionDto.fromDto();
        OptionEntity savedOption = optionsRepository.save(optionEntity);
        log.info("Saved option: {}", savedOption);
        return savedOption;

    }

    @Transactional
    @Override
    public OptionEntity updateOption(Long id, OptionDto updateOptionDto) {
        var loadOption = findOptionById(id);
        checkIfKeyAlreadyExists(updateOptionDto.getKey());
        return updateOptionFields(loadOption, updateOptionDto);
    }

    @Override
    public void deleteOptionById(Long id) {
        findOptionById(id);
        optionsRepository.deleteById(id);
        log.info("Deleted option by ID: {}", id);
    }

    @Transactional
    @Override
    public void updateOptionVisibility(Long id, String visible) {
        var option = findOptionById(id);
        option.changeVisibility(visible);
        log.info("For a option with ID {}, the visibility has been changed to: {}", id, visible);
    }

    private OptionEntity updateOptionFields(OptionEntity loadOption, OptionDto optionDto) {
        var updatedOption = loadOption.updateFields(optionDto);
        log.info("Updated option: {}", updatedOption);
        return updatedOption;
    }

    private void checkIfKeyAlreadyExists(String key) {
        if (optionsRepository.findByKey(key).isPresent()) {
            log.error("Option with key: {} is already registered", key);
            throw new OptionAlreadyExists(key);
        }

    }

    private List<OptionEntity> printLogInfo(List<OptionEntity> options) {
        log.info("Size of loaded options from database: {}", options.size());
        return options;
    }
}

