package ua.vixdev.gym.options.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.options.entity.OptionEntity;
import ua.vixdev.gym.options.exceptions.OptionAlreadyExists;
import ua.vixdev.gym.options.exceptions.OptionNotFoundException;
import ua.vixdev.gym.options.repository.OptionsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OptionService {
    private final OptionsRepository optionsRepository;

    public List<OptionEntity> findAllOptions() {
        return optionsRepository.findAll();
    }

    public OptionEntity findOptionById(Long id) {
        return optionsRepository.findById(id).orElseThrow(() -> new OptionNotFoundException(id));
    }

    @Transactional
    public OptionEntity createOption(OptionEntity option) {
        validateKeyExists(option.getKey());
        return optionsRepository.save(option);

    }

    @Transactional
    public OptionEntity updateOption(OptionEntity option) {
        validateOptionExists(option.getId());
        return optionsRepository.save(option);
    }

    public void deleteOptionById(Long id) {
        validateOptionExists(id);
        optionsRepository.deleteById(id);
    }


    private void validateKeyExists(String key) {
        optionsRepository.findByKey(key).orElseThrow(() -> new OptionAlreadyExists(key));
    }

    private void validateOptionExists(Long id) {
        optionsRepository.findById(id).orElseThrow(() -> new OptionNotFoundException(id));
    }
}

