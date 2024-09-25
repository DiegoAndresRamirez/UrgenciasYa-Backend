package com.urgenciasYa.infrastructure.config.openApi;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("UrgenciasYa API")
                        .version("1.0")
                        .description("API para el sistema UrgenciasYa, gestionando emergencias médicas.")) // Description of the API
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Add security requirement for bearer authentication
                .components(new io.swagger.v3.oas.models.Components() // Define components of the API
                        .addSecuritySchemes("bearerAuth", new SecurityScheme() // Add a security scheme for bearer authentication
                                .name("bearerAuth") // Name of the security scheme
                                .type(SecurityScheme.Type.HTTP) // Type of security scheme (HTTP)
                                .scheme("bearer") // Scheme type (Bearer)
                                .bearerFormat("JWT"))); // Format of the bearer token (JWT)


    }
}
