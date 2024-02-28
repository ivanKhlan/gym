package ua.vixdev.gym.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.vixdev.gym.security.config.AdminConfig;
import ua.vixdev.gym.security.filter.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(AdminConfig.class)
@EnableMethodSecurity(securedEnabled = true)
public class GymSecurityConfiguration {

    private final String secret;

    public GymSecurityConfiguration(@Value("${jwt.secret}") String secret){
        this.secret = secret;
    }
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http,
                                            AuthenticationManager authenticationManager,
                                            UserDetailsService userDetailsService) throws Exception {
        return http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/users/**").hasRole(UserRole.ROLE_ADMIN.getRole())
//                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userDetailsService, secret))
                .build();
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
