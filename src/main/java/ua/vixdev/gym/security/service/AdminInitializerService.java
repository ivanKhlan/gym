package ua.vixdev.gym.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.security.config.AdminConfig;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminService implements CommandLineRunner {

    private final AdminConfig adminConfig;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public void run(String... args) {
        UserEntity userEntity = new UserEntity(
                adminConfig.getFirstName(),
                adminConfig.getLastName(),
                adminConfig.getUsername(),
                passwordEncoder.encode(adminConfig.getPassword()),
                adminConfig.getPhoneNumber(),
                true,
                adminConfig.getRoles()
        );
        userRepository.save(userEntity);
    }
}
