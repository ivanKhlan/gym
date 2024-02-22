package ua.vixdev.gym.user.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-20
 */
@Configuration
public class UserMessageConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:messages/api_user_error_messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
