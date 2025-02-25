package ua.vixdev.gym.status.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.vixdev.gym.status.dto.StatusDto;


import java.sql.Timestamp;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "status")
@EntityListeners(AuditingEntityListener.class)
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "value", columnDefinition = "VARCHAR(70)")
    private String value;
    @Column(name = "visible")
    private boolean visible;
    @CreatedDate
    @Column(name = "created_at")
    private Timestamp created_at;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updated_at;
    @Column(name = "deleted_at")
    private Timestamp deleted_at;

    public StatusEntity(String value, boolean visible) {
        this.value = value;
        this.visible = visible;
    }

    public boolean equalValue(String value) {
        return this.value.equals(value);
    }
}
