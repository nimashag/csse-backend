package com.medilink.api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for security settings.
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean definition for the PasswordEncoder to use BCrypt hashing.
     * @return A PasswordEncoder instance that uses BCrypt for encoding passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
