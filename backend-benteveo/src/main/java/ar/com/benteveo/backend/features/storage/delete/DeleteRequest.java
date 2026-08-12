package ar.com.benteveo.backend.features.storage.delete;

import jakarta.validation.constraints.NotBlank;

public record DeleteRequest(
        @NotBlank(message = "El publicId es obligatorio")
        String publicId
) {}
