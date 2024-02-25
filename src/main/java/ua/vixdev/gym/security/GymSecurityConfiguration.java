package ua.vixdev.gym.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication()
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
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users/**").authenticated()
                        .anyRequest().permitAll())
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
    protected InMemoryUserDetailsManager configureAuthentication() {
        List<UserDetails> users = new ArrayList<>();
        List<GrantedAuthority> adminAuthority = new ArrayList<>();

        adminAuthority.add(new SimpleGrantedAuthority("ADMIN"));
        UserDetails admin = new User("admin", "{noop}pass", adminAuthority);
        users.add(admin);

        List<GrantedAuthority> userAuthority = new ArrayList<>();
        adminAuthority.add(new SimpleGrantedAuthority("USER"));
        UserDetails user = new User("user", "{noop}pass", userAuthority);
        users.add(user);

        return new InMemoryUserDetailsManager(users);
    }
}
