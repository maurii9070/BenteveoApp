package ar.com.benteveo.backend.features.product.create;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateProductResponse(
        UUID id,
        String title,
        String status,
        LocalDateTime createdAt
) {}
