package ua.vixdev.gym.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
/**
 * Entity class that represents history change
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "history_changes", schema = "public")
public class UserHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "text", length = 255)
    private String text;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
