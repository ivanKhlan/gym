package ua.vixdev.gym.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.vixdev.gym.user.dto.TypeDto;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Data
@Table(name = "types", schema = "public", catalog = "gym")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "visible")
    private Boolean visible;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public TypeEntity(String title, Boolean visible) {
        this.title = title;
        this.visible = Optional.ofNullable(visible).orElse(true);
    }


    public TypeEntity updateFields(TypeDto typeDto) {
        if (typeDto.getTitle() != null) {
            title = typeDto.getTitle();
        }

        if (typeDto.getVisible() != null) {
            visible = typeDto.getVisible();
        }
        return this;
    }

    public void changeTypeVisibility(String visible) {
        this.visible = Boolean.valueOf(visible);
    }
}
