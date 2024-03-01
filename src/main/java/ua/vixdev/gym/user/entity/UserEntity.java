package ua.vixdev.gym.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.vixdev.gym.security.model.UserRole;
import ua.vixdev.gym.user.controller.dto.GetUserDetailsDto;
import ua.vixdev.gym.user.controller.dto.GetUserDto;
import ua.vixdev.gym.user.controller.dto.UpdateUserDto;

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

    public UserEntity updateFields(UpdateUserDto createUserDto) {
        if (createUserDto.getFirstName() != null) {
            firstName = createUserDto.getFirstName();
        }

        if (createUserDto.getLastName() != null) {
            lastName = createUserDto.getLastName();
        }

        if (createUserDto.getEmail() != null) {
            email = createUserDto.getEmail();
        }

        if (createUserDto.getPhoneNumber() != null) {
            phoneNumber = createUserDto.getPhoneNumber();
        }

        if (createUserDto.getVisible() != null) {
            visible = createUserDto.getVisible();
        }
        return this;
    }

    public void changeUserVisibility(String visible) {
        this.visible = Boolean.valueOf(visible);
    }

    public void changePassword(String password) {
        if (StringUtils.isNotBlank(password)) {
            this.password = password;
        }
    }

    public GetUserDto toGetUserDto() {
        return new GetUserDto(
                id,
                firstName,
                lastName,
                email,
                password,
                photoUrl,
                visible,
                roles,
                createdAt,
                updatedAt
        );
    }

    public GetUserDetailsDto toGetUserDetailsDto() {
        return new GetUserDetailsDto(
                id,
                firstName,
                lastName,
                email,
                password,
                photoUrl,
                visible,
                roles,
                createdAt,
                updatedAt,
                rememberToken,
                emailVerifiedAt,
                phoneNumber,
                deletedAt
        );
    }
}

