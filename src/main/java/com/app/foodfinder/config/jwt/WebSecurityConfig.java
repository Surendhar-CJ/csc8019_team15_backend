package com.app.foodfinder.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures web security settings for the application.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Configures CORS settings for the application.
     *
     * @return a WebMvcConfigurer object that contains the CORS configuration.
     *
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "PUT", "POST", "DELETE");
            }
        };
    }



}