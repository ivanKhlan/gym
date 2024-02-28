package ua.vixdev.gym.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.security.config.AdminConfig;
import ua.vixdev.gym.security.model.GymUserDetails;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class GymUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminConfig config;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findById(Long.valueOf(username));
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            var userDetails = new GymUserDetails(
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.getRoles()
                            .stream()
                            .map(userRole -> (GrantedAuthority) userRole::name)
                            .toList());
            userDetails.setId(userEntity.getId());
            return userDetails;
        }
        log.error("User with id: {}, not found", username);
        throw new UsernameNotFoundException("User with id: %s, not found".formatted(username));
    }
}
