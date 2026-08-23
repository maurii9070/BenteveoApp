package ar.com.benteveo.backend.features.product.create;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateProductResponse(
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(example = "Martillo profesional")
        String title,

        @Schema(example = "DRAFT")
        String status,

        @Schema(example = "2026-08-23T20:00:00")
        LocalDateTime createdAt
) {}
