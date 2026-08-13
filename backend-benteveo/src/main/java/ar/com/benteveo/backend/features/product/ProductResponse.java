package ar.com.benteveo.backend.features.product;

import ar.com.benteveo.backend.features.product.photos.PhotoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String title,
        String description,
        BigDecimal priceDay,
        BigDecimal priceWeek,
        BigDecimal priceMonth,
        BigDecimal deposit,
        Boolean isActive,
        String status,
        BigDecimal ratingAvg,
        Integer ratingCount,
        String categoryName,
        UUID ownerId,
        List<PhotoResponse> photos,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
