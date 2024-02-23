package ua.vixdev.gym.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResetPasswordToken {
    private Integer tokenBody;

    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private String email;

    public boolean isTokenExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }
}
