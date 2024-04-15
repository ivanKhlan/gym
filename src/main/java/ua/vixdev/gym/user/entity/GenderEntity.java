package ua.vixdev.gym.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.vixdev.gym.user.dto.GenderDto;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Data
@Table(name = "genders", schema = "public", catalog = "gym")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenderEntity {

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

    public GenderEntity(String title, Boolean visible) {
        this.title = title;
        this.visible = Optional.ofNullable(visible).orElse(true);
    }


    public GenderEntity updateFields(GenderDto genderDto) {
        if (genderDto.getTitle() != null) {
            title = genderDto.getTitle();
        }

        if (genderDto.getVisible() != null) {
            visible = genderDto.getVisible();
        }
        return this;
    }

    public void changeGenderVisibility(String visible) {
        this.visible = Boolean.valueOf(visible);
    }
}
