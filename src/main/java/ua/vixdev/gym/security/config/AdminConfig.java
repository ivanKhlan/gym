package ua.vixdev.gym.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
@Data
@ConfigurationProperties("app.security.admin")
public class AdminConfig {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private Set<String> roles;

    public User adminUser() {
        return new User(
                username,
                password,
                roles.stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet())
        );
    }
}
