package ar.com.benteveo.backend.shared.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI benteveoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Benteveo API")
                        .version("1.0.0")
                        .description("API de Benteveo para la gestión de productos, reservas y usuarios."))
                .tags(List.of(
                        new Tag().name("Auth").description("Autenticación y gestión de sesión del usuario"),
                        new Tag().name("Products").description("Gestión del catálogo de productos")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Ingresá el token JWT obtenido en POST /api/v1/auth/login")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
