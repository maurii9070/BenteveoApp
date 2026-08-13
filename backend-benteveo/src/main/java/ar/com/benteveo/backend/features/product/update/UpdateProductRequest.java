package ar.com.benteveo.backend.features.product.update;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String title,
        String description,
        BigDecimal priceDay,
        BigDecimal priceWeek,
        BigDecimal priceMonth,
        BigDecimal deposit,
        Long categoryId,
        Boolean isActive,
        String status
) {}
