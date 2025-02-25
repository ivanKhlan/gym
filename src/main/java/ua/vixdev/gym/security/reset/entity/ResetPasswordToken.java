package ua.vixdev.gym.security.reset.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.vixdev.gym.user.entity.UserEntity;

import java.time.LocalDateTime;
@Table(name = "tokens")
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

    private String tokenBody;
    private LocalDateTime expiryDate;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private UserEntity user;
}