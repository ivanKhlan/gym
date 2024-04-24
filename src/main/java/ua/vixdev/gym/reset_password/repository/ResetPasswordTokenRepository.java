package ua.vixdev.gym.reset_password.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.reset_password.entity.ResetPasswordToken;

import java.util.Optional;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByTokenBody(String tokenBody);
}
