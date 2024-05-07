package ua.vixdev.gym.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.application.entity.TypeEntity;
import ua.vixdev.gym.application.exception.TypeNotFoundException;
import ua.vixdev.gym.application.repository.TypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    public List<TypeEntity> findAll() {
        return typeRepository.findAll();
    }

    public TypeEntity findById(Long id) {
        return typeRepository.findById(id).orElseThrow(() -> new TypeNotFoundException(id));
    }

    public TypeEntity createType(TypeEntity type) {
        return typeRepository.save(type);
    }

    public TypeEntity updateType(TypeEntity type) {
        validateTypeExists(type.getId());
        return typeRepository.save(type);
    }

    public void deleteById(Long id) {
        validateTypeExists(id);
        typeRepository.deleteById(id);
    }

    private void validateTypeExists(Long id) {
        typeRepository.findById(id).orElseThrow(() -> new TypeNotFoundException(id));
    }
}
