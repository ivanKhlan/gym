package ua.vixdev.gym.application.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entity class representing an application.
 */
@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "applications")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "backend_version", nullable = false)
  private String backEndVersion;

  @Column(name = "frontend_version", nullable = false)
  private String frontEndVersion;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String image;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String key;

  private String text;

  @Column(nullable = false)
  private String licenseType;

  @Column(nullable = false)
  private Long typeId;

  @CreatedDate
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  private LocalDateTime deletedAt;
}
