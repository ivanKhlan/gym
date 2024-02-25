package ua.vixdev.gym.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.vixdev.gym.dto.SubscriptionDto;
import ua.vixdev.gym.entity.Subscription;
import ua.vixdev.gym.mapper.SubscriptionMapper;
import ua.vixdev.gym.repository.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    public void getAllSubscriptions_ReturnsListOfSubscriptions() {
        // Arrange
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription(1L, "Description 1", true, 10.00, "image1.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "Text 1", "Title 1", 1, "Doe"));
        subscriptions.add(new Subscription(2L, "Description 2", true, 20.00, "image2.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "Text 2", "Title 2", 2, "Smith"));

        when(subscriptionRepository.findAll()).thenReturn(subscriptions);
        when(subscriptionMapper.toDTO(any())).thenAnswer(invocation -> {
            Subscription subscription = invocation.getArgument(0);
            return new SubscriptionDto(subscription.getId(), subscription.getDescription(), subscription.isFreezing(), subscription.getPrice(), subscription.getImage(), subscription.getStartDate(), subscription.getEndDate(), subscription.getDaysFreezing(), subscription.getExpirationAt(), subscription.isDiscount(), subscription.getDiscountSum(), subscription.getDiscountDate(), subscription.getText(), subscription.getTitle(), subscription.getStatusId(), subscription.getLastName());
        });

        // Act
        List<SubscriptionDto> result = subscriptionService.getAllSubscriptions();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Description 1", result.get(0).getDescription());
        assertEquals("Description 2", result.get(1).getDescription());
    }

    @Test
    public void createSubscription_ReturnsCreatedSubscription() {
        // Arrange
        SubscriptionDto requestDto = new SubscriptionDto(null, "New Subscription", true, 30.00, "new_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "New Text", "New Title", 3, "Johnson");
        Subscription createdSubscription = new Subscription(1L, "New Subscription", true, 30.00, "new_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "New Text", "New Title", 3, "Johnson");

        when(subscriptionMapper.toEntity(requestDto)).thenReturn(new Subscription(null, "New Subscription", true, 30.00, "new_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "New Text", "New Title", 3, "Johnson"));
        when(subscriptionRepository.save(any())).thenReturn(createdSubscription);
        when(subscriptionMapper.toDTO(createdSubscription)).thenReturn(requestDto);

        // Act
        SubscriptionDto result = subscriptionService.createSubscription(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals("New Subscription", result.getDescription());
    }

    @Test
    public void updateSubscription_WhenSubscriptionExists_ReturnsUpdatedSubscription() {
        // Arrange
        Long id = 1L;
        SubscriptionDto requestDto = new SubscriptionDto(id, "Updated Subscription", true, 40.00, "updated_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "Updated Text", "Updated Title", 4, "Brown");
        Subscription existingSubscription = new Subscription(id, "Existing Subscription", true, 30.00, "existing_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "Existing Text", "Existing Title", 3, "Johnson");
        Subscription updatedSubscription = new Subscription(id, "Updated Subscription", true, 40.00, "updated_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "Updated Text", "Updated Title", 4, "Brown");

        when(subscriptionRepository.existsById(id)).thenReturn(true);
        when(subscriptionMapper.toEntity(requestDto)).thenReturn(new Subscription(id, "Updated Subscription", true, 40.00, "updated_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "Updated Text", "Updated Title", 4, "Brown"));
        when(subscriptionRepository.save(any())).thenReturn(updatedSubscription);
        when(subscriptionMapper.toDTO(updatedSubscription)).thenReturn(requestDto);

        // Act
        Optional<SubscriptionDto> result = subscriptionService.updateSubscription(id, requestDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Subscription", result.get().getDescription());
    }

    @Test
    public void deleteSubscription_WhenSubscriptionExists_ReturnsTrue() {
        // Arrange
        Long id = 1L;
        when(subscriptionRepository.existsById(id)).thenReturn(true);

        // Act
        boolean result = subscriptionService.deleteSubscription(id);

        // Assert
        assertTrue(result);
    }

    @Test
    public void deleteSubscription_WhenSubscriptionDoesNotExist_ReturnsFalse() {
        // Arrange
        Long id = 1L;
        when(subscriptionRepository.existsById(id)).thenReturn(false);

        // Act
        boolean result = subscriptionService.deleteSubscription(id);

        // Assert
        assertFalse(result);
    }
}
