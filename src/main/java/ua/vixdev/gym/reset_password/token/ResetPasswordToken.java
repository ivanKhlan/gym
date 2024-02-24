package ua.vixdev.gym.reset_password.token;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResetPasswordToken {
    @Id
    @GeneratedValue
    private Long id;
}