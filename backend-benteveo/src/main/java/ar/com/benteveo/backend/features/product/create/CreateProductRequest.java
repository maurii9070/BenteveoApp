package ar.com.benteveo.backend.features.product.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank(message = "El titulo es obligatorio")
        @Schema(example = "Martillo profesional")
        String title,

        @NotBlank(message = "La descripcion es obligatoria")
        @Schema(example = "Martillo de carpintero con mango de madera, ideal para trabajos de precisión.")
        String description,

        @NotNull(message = "El precio diario es obligatorio")
        @Min(value = 0, message = "El precio diario no puede ser negativo")
        @Schema(example = "1500.00")
        BigDecimal priceDay,

        @Min(value = 0, message = "El precio semanal no puede ser negativo")
        @Schema(example = "9000.00")
        BigDecimal priceWeek,

        @NotNull(message = "El precio mensual es obligatorio")
        @Min(value = 0, message = "El precio mensual no puede ser negativo")
        @Schema(example = "30000.00")
        BigDecimal priceMonth,

        @NotNull(message = "La seña es obligatoria")
        @Min(value = 0, message = "La seña no puede ser negativa")
        @Schema(example = "5000.00")
        BigDecimal deposit,

        @NotNull(message = "El ID de la categoria es obligatorio")
        @Schema(example = "1")
        Long categoryId
) {}
