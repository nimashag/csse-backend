package com.medilink.api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for web-related settings, such as CORS.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configure CORS mappings for the application.
     * @param registry The CORS registry to configure.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")  // Allow all origins (wildcard)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow all needed methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(false);  // Disable credentials, required when using wildcard for origins
    }
}

