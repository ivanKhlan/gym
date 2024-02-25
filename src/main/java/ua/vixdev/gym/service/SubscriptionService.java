package ua.vixdev.gym.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.dto.SubscriptionDto;
import ua.vixdev.gym.entity.Subscription;
import ua.vixdev.gym.mapper.SubscriptionMapper;
import ua.vixdev.gym.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
    }

    public List<SubscriptionDto> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionMapper.toEntity(subscriptionDto);
        Subscription createdSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(createdSubscription);
    }

    public Optional<SubscriptionDto> getSubscription(Long id) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        return optionalSubscription.map(subscriptionMapper::toDTO);
    }

    public Optional<SubscriptionDto> updateSubscription(Long id, SubscriptionDto subscriptionDto) {
        if (!subscriptionRepository.existsById(id)) {
            return Optional.empty();
        }
        Subscription subscription = subscriptionMapper.toEntity(subscriptionDto);
        subscription.setId(id);
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return Optional.of(subscriptionMapper.toDTO(updatedSubscription));
    }

    public boolean deleteSubscription(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            return false;
        }
        subscriptionRepository.deleteById(id);
        return true;
    }
}
