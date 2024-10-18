package com.medilink.api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")  // Allow all origins (wildcard)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow all needed methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(false);  // Disable credentials, required when using wildcard for origins
    }
}

