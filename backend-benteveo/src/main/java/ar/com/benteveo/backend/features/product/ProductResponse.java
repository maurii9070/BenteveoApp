package ar.com.benteveo.backend.features.product;

import ar.com.benteveo.backend.features.product.photos.PhotoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(example = "Martillo profesional")
        String title,

        @Schema(example = "Martillo de carpintero con mango de madera, ideal para trabajos de precisión.")
        String description,

        @Schema(example = "1500.00")
        BigDecimal priceDay,

        @Schema(example = "9000.00")
        BigDecimal priceWeek,

        @Schema(example = "30000.00")
        BigDecimal priceMonth,

        @Schema(example = "5000.00")
        BigDecimal deposit,

        @Schema(example = "true")
        Boolean isActive,

        @Schema(example = "PUBLISHED")
        String status,

        @Schema(example = "4.5")
        BigDecimal ratingAvg,

        @Schema(example = "12")
        Integer ratingCount,

        @Schema(example = "Herramientas")
        String categoryName,

        @Schema(example = "9b2c1f88-1234-5678-90ab-cdef01234567")
        UUID ownerId,

        List<PhotoResponse> photos,

        @Schema(example = "2026-08-23T20:00:00")
        LocalDateTime createdAt,

        @Schema(example = "2026-08-23T20:30:00")
        LocalDateTime updatedAt
) {}
