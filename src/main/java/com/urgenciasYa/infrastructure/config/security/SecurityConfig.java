package com.urgenciasYa.infrastructure.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    private final String[] PUBLIC_RESOURCES = {
            "/api/v1/users/login",
            "/api/v1/users/register",
            "/api/v1/towns/getAll",
            "/api/v1/eps/getAll",
            "/api/v1/hospitals/filter",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/api/v1/hospitals/{id}",
    };

    private final String[] ADMIN_RESOURCES = {
            "/api/v1/towns/**",
            "/api/v1/eps/**",
            "/api/v1/hospitals/**",
            "/api/shifts/**",
            "/api/v1/users/register",
            "/api/v1/users/login"
    };

    private final String[] USER_RESOURCES = {
            "/api/v1/contacts/**",
            "/api/v1/towns/getAll",
            "/api/v1/hospitals/filter",
            "/api/v1/hospitals/{id}",
            "/api/v1/eps/getAll",
            "/api/v1/eps/{id}",
            "/api/v1/users/{id}",
            "/api/v1/users/{id}/change-password",
            "/api/v1/users/update/{id}",
            "/api/v1/users/register",
            "/api/v1/users/login"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    var source = new org.springframework.web.cors.CorsConfiguration();
                    source.setAllowedOrigins(List.of("http://localhost:3000")); // Agrega aquí tus orígenes permitidos
                    source.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    source.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    return source;
                }))
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_RESOURCES).permitAll()
                        .requestMatchers(USER_RESOURCES).hasAuthority("USER")
                        .requestMatchers(ADMIN_RESOURCES).hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Construye el SecurityFilterChain
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
