package ua.vixdev.gym.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.user.dto.GenderDto;
import ua.vixdev.gym.user.entity.GenderEntity;
import ua.vixdev.gym.user.exceptions.buisnes_logic.GenderNotFoundException;
import ua.vixdev.gym.user.repository.GenderRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenderServiceImpl implements GenderService {
    private final GenderRepository genderRepository;

    @Transactional
    @Override
    public GenderEntity createNewGender(GenderDto gender) {

        var genderEntity = gender.toGenderEntity();
        GenderEntity savedGender = genderRepository.save(genderEntity);
        log.info("Saved gender with ID: {}", savedGender.getId());
        return savedGender;
    }

    @Transactional
    @Override
    public GenderEntity findGenderById(Long id) {
        var loadGender = genderRepository.findById(id);
        log.info("Loaded gender with ID: {}", id);

        return loadGender.orElseThrow(() -> {
            log.error("Gender with ID: {} not found!", id);
            return new GenderNotFoundException(id);
        });
    }

    @Transactional
    @Override
    public GenderEntity updateGender(Long id, GenderDto genderDto) {
        var loadGender = findGenderById(id);

        return updateGenderFields(loadGender, genderDto);
    }

    @Transactional
    @Override
    public void deleteGenderById(Long id) {
        findGenderById(id);
        genderRepository.deleteById(id);
        log.info("Deleted user with ID: {}", id);
    }

    private GenderEntity updateGenderFields(GenderEntity loadGender, GenderDto genderDto) {
        var updatedGender = loadGender.updateFields(genderDto);
        log.info("Updated gender with ID: {}", updatedGender.getId());
        return updatedGender;
    }
}
