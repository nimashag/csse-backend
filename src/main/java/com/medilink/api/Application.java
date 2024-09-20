package com.medilink.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the MediLink application.
 */
@SpringBootApplication
public class Application {

    /**
     * The main method which starts the Spring Boot application.
     * @param args Command line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // Launch the Spring Boot application
    }

}