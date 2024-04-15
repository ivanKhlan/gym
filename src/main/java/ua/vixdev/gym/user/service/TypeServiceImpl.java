package ua.vixdev.gym.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.user.dto.TypeDto;
import ua.vixdev.gym.user.entity.TypeEntity;
import ua.vixdev.gym.user.exceptions.buisnes_logic.TypeNotFoundException;
import ua.vixdev.gym.user.repository.TypeRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    @Override
    public TypeEntity createNewType(TypeDto type) {

        var typeEntity = type.toGenderEntity();
        TypeEntity savedType = typeRepository.save(typeEntity);
        log.info("Saved type with ID: {}", savedType.getId());
        return savedType;
    }

    @Override
    public TypeEntity findTypeById(Long id) {

        var loadType = typeRepository.findById(id);
        log.info("Loaded type with ID: {}", id);

        return loadType.orElseThrow(() -> {
            log.error("Type with ID: {} not found!", id);
            return new TypeNotFoundException(id);
        });
    }

    @Override
    public TypeEntity updateType(Long id, TypeDto typeDto) {

        var loadType = findTypeById(id);
        return updateTypeFields(loadType, typeDto);
    }

    @Override
    public void deleteTypeById(Long id) {

        findTypeById(id);
        typeRepository.deleteById(id);
        log.info("Deleted type with ID: {}", id);
    }


    private TypeEntity updateTypeFields(TypeEntity loadType, TypeDto typeDto) {
        var updatedType = loadType.updateFields(typeDto);
        log.info("Updated type with ID: {}", updatedType.getId());
        return updatedType;
    }
}
