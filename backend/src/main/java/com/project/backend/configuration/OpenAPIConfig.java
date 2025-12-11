package com.project.backend.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                description = "Это API предоставляет эндпоинты для сервиса бронирования билетов на мероприятие.",
                title = "Системное API для бронирования билетов.",
                version = "1.0"
        ),
        servers = {
                @io.swagger.v3.oas.annotations.servers.Server(
                        description = "Local ENV",
                        url = "http://localhost:8090"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth desc",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
