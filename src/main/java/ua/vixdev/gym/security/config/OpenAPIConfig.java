package ua.vixdev.gym.security.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("JWT Token",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER).name("Authorization")))
                .info(new Info().title("Gym API").version("0"))
                .addSecurityItem(
                        new SecurityRequirement().addList("JWT Token", Arrays.asList("read", "write")));
    }
}