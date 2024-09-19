package com.urgenciasYa.infrastructure.config.security;

import com.urgenciasYa.domain.model.RoleEntity;
import com.urgenciasYa.domain.model.UserEntity;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    private final String[] PUBLIC_RESOURCES = {"/login","register","/api/v1/town/getAll","/api/v1/eps/getAll","/api/v1/hospitals/filter"};
    private final String[] ADMIN_RESOURCES = {"login","/api/v1/town/**","/api/v1/eps/**","/api/v1/hospitals/**","/api/shifts/**"};
    private final String[] USER_RESOURCES = {"/api/v1/contacts/","/api/v1/town/getAll","/api/v1/hospitals/filter","/api/v1/eps/getAll","/api/v1/eps/{id}"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



        return http.csrf(customizer -> customizer.disable()).
                authorizeHttpRequests(request -> request
                        .requestMatchers(PUBLIC_RESOURCES).permitAll()
                        .requestMatchers(ADMIN_RESOURCES).hasAuthority("ADMIN")
                        .requestMatchers(USER_RESOURCES).hasAuthority("USER")
                        .anyRequest().authenticated()).
                httpBasic(Customizer.withDefaults()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


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
