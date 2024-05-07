package ua.vixdev.gym.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.vixdev.gym.user.controller.dto.UserDto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-20
 */
@Entity
@Data
@Table(name = "users", schema = "public")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -1446398935944895849L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRoleEntity> roles = new HashSet<>();

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "remember_token")
    private String rememberToken;
    @Column(name = "photo")
    private String photoUrl;
    @Column(name = "phone_number")
    private String phoneNumber;
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


    public UserEntity(String firstName, String lastName, String email, String password, String phoneNumber, Boolean visible) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.visible = Optional.ofNullable(visible).orElse(true);
    }

    public void addPassword(String password) {
        this.password = password;
    }

    public void addRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
    }

    public void updateFields(UserDto userDto) {
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        this.phoneNumber = userDto.getPhoneNumber();
        this.visible = userDto.getVisible();
    }

    public boolean equalEmails(String email) {
        return this.email.equalsIgnoreCase(email);
    }
}

