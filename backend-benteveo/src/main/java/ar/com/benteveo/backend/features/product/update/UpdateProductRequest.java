package ar.com.benteveo.backend.features.product.update;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record UpdateProductRequest(
        @Schema(example = "Martillo profesional actualizado")
        String title,

        @Schema(example = "Martillo reforzado, nuevo modelo con mejor agarre.")
        String description,

        @Schema(example = "1800.00")
        BigDecimal priceDay,

        @Schema(example = "10000.00")
        BigDecimal priceWeek,

        @Schema(example = "32000.00")
        BigDecimal priceMonth,

        @Schema(example = "6000.00")
        BigDecimal deposit,

        @Schema(example = "1")
        Long categoryId,

        @Schema(example = "true")
        Boolean isActive,

        @Schema(example = "PUBLISHED")
        String status
) {}
