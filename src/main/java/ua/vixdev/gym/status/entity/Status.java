package ua.vixdev.gym.status.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.vixdev.gym.options.dto.UpdateOptionDto;
import ua.vixdev.gym.options.entity.Options;
import ua.vixdev.gym.status.dto.UpdateStatusDto;

import java.sql.Timestamp;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "status")
@EntityListeners(AuditingEntityListener.class)
public class Status {

    /**
CREATE TABLE `status` (
    `id` SERIAL PRIMARY KEY,
    `value` VARCHAR(70) NOT NULL ,
    `visible` BOOLEAN DEFAULT 1,
    `created_at` TIMESTAMP DEFAULT NOW(),
    `updated_at` TIMESTAMP DEFAULT NOW(),
    `deleted_at` TIMESTAMP DEFAULT NULL
);
);
     */
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

    public Status(String value,
                  boolean visible) {
        this.value = value;
        this.visible = visible;
    }

    public void changeVisibility(String  visible){
        this.visible = Boolean.parseBoolean(visible);
    }
    public Status updateFields(UpdateStatusDto updateStatusDto){
        if (updateStatusDto.getValue() != null) {
            value = updateStatusDto.getValue();
        }
        visible = updateStatusDto.isVisible();
        return this;
    }
}
