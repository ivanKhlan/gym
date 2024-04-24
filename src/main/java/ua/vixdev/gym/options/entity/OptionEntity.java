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
public class OptionEntity {
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

    public OptionEntity(boolean autoload,
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
    public OptionEntity updateFields(OptionDto updateOptionDto){
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

