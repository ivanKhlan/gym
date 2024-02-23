package ua.vixdev.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "value", length = 70, nullable = false)
    private String value;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public TypeEntity(String value, Boolean visible, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        this.value = value;
        this.visible = visible;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    @PrePersist
    public void onSave() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

}
