package ar.com.benteveo.backend.features.category;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryResponse(
        @Schema(example = "1")
        Long id,

        @Schema(example = "Herramientas")
        String name,

        @Schema(example = "herramientas")
        String slug
) {}