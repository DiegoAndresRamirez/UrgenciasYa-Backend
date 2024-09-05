package com.urgenciasYa.config.openApi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;


@Configuration
@OpenAPIDefinition(info = @Info(
        title = "API UrgenciasYa",
        version = "1.0",
        description = "UrgenciasYa Application API Documentation"
))
public class SwaggerConfig {
}
