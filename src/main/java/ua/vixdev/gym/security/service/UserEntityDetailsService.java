package ua.vixdev.gym.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.security.model.UserEntityDetails;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.repository.UserRepository;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserEntityDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findById(Long.valueOf(username));
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            var userDetails = new UserEntityDetails(
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.getRoles()
                            .stream()
                            .map(userRole -> (GrantedAuthority) userRole::name)
                            .toList());
            userDetails.setId(userEntity.getId());
            return userDetails;
        }
        log.warn("User with id: {}, not found", username);
        throw new UsernameNotFoundException("User with id: %s, not found".formatted(username));
    }
}
