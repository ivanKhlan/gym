package ua.vixdev.gym.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.security.config.AdminConfig;
import ua.vixdev.gym.security.model.UserRole;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.repository.UserRepository;

import java.util.Set;
/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminInitializerService implements CommandLineRunner {

    private final AdminConfig adminConfig;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * This method is used to add an admin to the database.
     */
    @Override
    public void run(String... args) {
        log.debug("Entering in run Method...");
        try {
            final var userEntity = new UserEntity(
                    adminConfig.getFirstName(),
                    adminConfig.getLastName(),
                    adminConfig.getUsername(),
                    passwordEncoder.encode(adminConfig.getPassword()),
                    adminConfig.getPhoneNumber(),
                    true,
                    Set.of(UserRole.ROLE_ADMIN)
            );
            UserEntity savedUser = userRepository.save(userEntity);
            log.info("Add Admin: {} to the DB...", savedUser.getEmail());
        } catch (Exception e) {
            log.error("Unknown error while adding Admin to DB. Error message: {}", e.getMessage());
        }
    }
}
