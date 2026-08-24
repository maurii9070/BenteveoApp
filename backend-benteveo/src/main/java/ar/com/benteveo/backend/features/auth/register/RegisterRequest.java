package ar.com.benteveo.backend.features.auth.register;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato de email no es válido")
        @Schema(example = "user@example.com")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        @Schema(example = "Secret123")
        String password,

        @NotBlank(message = "El nombre es obligatorio")
        @Schema(example = "Juan")
        String firstName,

        @NotBlank(message = "El apellido es obligatorio")
        @Schema(example = "Pérez")
        String lastName,

        @NotBlank(message = "El DNI es obligatorio")
        @Schema(example = "30123456")
        String dni
) {}
