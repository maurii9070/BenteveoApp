package ar.com.benteveo.backend.features.auth.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato de email no es válido")
        @Schema(example = "user@example.com")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Schema(example = "Secret123")
        String password
) {}
