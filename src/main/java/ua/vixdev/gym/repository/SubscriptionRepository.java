package ua.vixdev.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.entity.Subscription;
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
