package com.juanda.powerup.plazoletaservice.infrastructure.configuration;


import com.juanda.powerup.plazoletaservice.infrastructure.configuration.exception.SecurityConfigurationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        try {
            return http
                    .csrf(csrf -> csrf.disable()) // API REST stateless, CSRF no aplica
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**"
                            ).permitAll()
                            .anyRequest().permitAll()
                    )
                    .build();

        } catch (Exception exception) {
            throw new SecurityConfigurationException(
                    "Error configuring security",
                    exception
            );
        }
    }
}