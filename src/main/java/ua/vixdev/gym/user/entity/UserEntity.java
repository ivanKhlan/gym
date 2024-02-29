package ua.vixdev.gym.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.vixdev.gym.security.model.UserRole;
import ua.vixdev.gym.user.dto.UserDto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-20
 */
@Entity
@Data
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "remember_token")
    private String rememberToken;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "phone")
    private String phoneNumber;
    @Column(name = "visible")
    private Boolean visible;
    @CollectionTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    public UserEntity(String firstName, String lastName, String email, String password, String phoneNumber, Boolean visible, Set<UserRole> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.visible = Optional.ofNullable(visible).orElse(true);
        this.roles = roles;
    }

    public UserEntity updateFields(UserDto userDto) {
        if (userDto.getFirstName() != null) {
            firstName = userDto.getFirstName();
        }

        if (userDto.getLastName() != null) {
            lastName = userDto.getLastName();
        }

        if (userDto.getEmail() != null) {
            email = userDto.getEmail();
        }

        if (userDto.getPassword() != null) {
            password = userDto.getPassword();
        }

        if (userDto.getPhoneNumber() != null) {
            phoneNumber = userDto.getPhoneNumber();
        }

        if (userDto.getVisible() != null) {
            visible = userDto.getVisible();
        }
        return this;
    }

    public void changeUserVisibility(String visible) {
        this.visible = Boolean.valueOf(visible);
    }

    public boolean equalsEmail(String anotherEmail) {
        return this.email.equals(anotherEmail);
    }
}

