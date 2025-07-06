package com.ciberpet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF por ahora para simplificar
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permitir todas las peticiones sin autenticación por ahora
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Este es el codificador que usaremos para hashear las contraseñas
        return new BCryptPasswordEncoder();
    }
}