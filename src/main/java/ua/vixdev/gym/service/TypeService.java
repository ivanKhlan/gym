package ua.vixdev.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.entity.TypeEntity;
import ua.vixdev.gym.repository.TypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    public List<TypeEntity> getAllTypes() {
        return typeRepository.findAll();
    }

    public Optional<TypeEntity> getTypeById(Long id) {
        return typeRepository.findById(id);
    }

    public TypeEntity saveTypeEntity(TypeEntity typeEntity) {
        return typeRepository.save(typeEntity);
    }

    public void deleteTypeById(Long id) {
        typeRepository.deleteById(id);
    }
}
