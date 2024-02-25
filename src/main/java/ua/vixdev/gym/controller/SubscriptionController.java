package ua.vixdev.gym.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.dto.SubscriptionDto;
import ua.vixdev.gym.service.SubscriptionService;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/subscriptions")

public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public List<SubscriptionDto> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> createSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        SubscriptionDto createdSubscription = subscriptionService.createSubscription(subscriptionDto);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable Long id) {
        Optional<SubscriptionDto> subscriptionDto = subscriptionService.getSubscription(id);
        return subscriptionDto.map(subscription -> new ResponseEntity<>(subscription, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDto> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionDto subscriptionDto) {
        Optional<SubscriptionDto> updatedSubscription = subscriptionService.updateSubscription(id, subscriptionDto);
        return updatedSubscription.map(subscription -> new ResponseEntity<>(subscription, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        boolean deleted = subscriptionService.deleteSubscription(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
