package ar.com.benteveo.backend.features.product.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank(message = "El titulo es obligatorio")
        String title,

        @NotBlank(message = "La descripcion es obligatoria")
        String description,

        @NotNull(message = "El precio diario es obligatorio")
        @Min(value = 0, message = "El precio diario no puede ser negativo")
        BigDecimal priceDay,

        @Min(value = 0, message = "El precio semanal no puede ser negativo")
        BigDecimal priceWeek,

        @NotNull(message = "El precio mensual es obligatorio")
        @Min(value = 0, message = "El precio mensual no puede ser negativo")
        BigDecimal priceMonth,

        @NotNull(message = "La seña es obligatoria")
        @Min(value = 0, message = "La seña no puede ser negativa")
        BigDecimal deposit,

        @NotNull(message = "El ID de la categoria es obligatorio")
        Long categoryId
) {}
