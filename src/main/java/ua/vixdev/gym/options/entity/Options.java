package ua.vixdev.gym.options.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.vixdev.gym.options.dto.OptionDto;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "options")
@EntityListeners(AuditingEntityListener.class)
public class Options {
    /**
CREATE TABLE `options` (
    `id` SERIAL PRIMARY KEY,
    `autoload` BOOLEAN DEFAULT 1,
    `key` VARCHAR(70) NOT NULL,
    `value` TEXT NULL,
    `visible` BOOLEAN DEFAULT 1,
    `created_at` TIMESTAMP DEFAULT NOW(),
    `updated_at` TIMESTAMP DEFAULT NOW(),
    `deleted_at` TIMESTAMP DEFAULT NULL
);
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "autoload")
    private boolean autoload;
    @Column(name = "key", columnDefinition = "VARCHAR(70)", nullable = false)
    private String key;
    @Column(name = "value", columnDefinition = "TEXT")
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

    public Options(boolean autoload,
                   String key,
                   String value,
                   boolean visible) {
        this.autoload = autoload;
        this.key = key;
        this.value = value;
        this.visible = visible;
    }

    public void changeVisibility(String  visible){
        this.visible = Boolean.parseBoolean(visible);
    }
    public Options updateFields(OptionDto updateOptionDto){
        if (updateOptionDto.getKey()!= null) {
            key = updateOptionDto.getKey();
        }
        if (updateOptionDto.getValue() != null) {
            value = updateOptionDto.getValue();
        }
        visible = updateOptionDto.isVisible();

        autoload = updateOptionDto.isAutoload();

        return this;
    }

}

