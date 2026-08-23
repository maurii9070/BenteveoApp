package ar.com.benteveo.backend.features.auth.register;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record RegisterResponse(
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(example = "user@example.com")
        String email,

        @Schema(example = "Juan")
        String firstName,

        @Schema(example = "Pérez")
        String lastName
) {}
