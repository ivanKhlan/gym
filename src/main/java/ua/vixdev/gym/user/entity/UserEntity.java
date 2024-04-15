package ua.vixdev.gym.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ua.vixdev.gym.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-20
 */
@Entity
@Data
@Table(name = "users", schema = "public", catalog = "gym")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

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
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "notation")
    private String notation;

    @Column(name = "birthday", nullable = false)
    private String birthday;

    @Column(name = "gender_id", nullable = false)
    private Integer genderId;

    @Column(name = "type_id", columnDefinition = "integer default 1")
    private Integer typeId;


    public UserEntity(String firstName, String lastName, String email, String password,
                      String phoneNumber, Boolean visible, String notation,
                      String birthday, Integer genderId, Integer typeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.visible = Optional.ofNullable(visible).orElse(true);
        this.notation = notation;
        this.birthday = birthday;
        this.genderId = genderId;
        this.typeId = Optional.ofNullable(typeId).orElse(1);
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

        if (userDto.getNotation() != null) {
            notation = userDto.getNotation();
        }

        if (userDto.getBirthday() != null) {
            birthday = userDto.getBirthday();
        }

        if (userDto.getGenderId() != null) {
            genderId = userDto.getGenderId();
        }

        if (userDto.getTypeId() != null) {
            typeId = userDto.getTypeId();
        }
        return this;
    }

    public void changeUserVisibility(String visible) {
        this.visible = Boolean.valueOf(visible);
    }

    public boolean equalsEmail(String anotherEmail) {
        return this.email.equals(anotherEmail);
    }
    public void changeUserRole(Integer role) {
        this.typeId = role;
    }
}

