package ua.vixdev.gym.security.reset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.security.reset.entity.ResetPasswordToken;

import java.util.Optional;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByTokenBody(String tokenBody);
}
