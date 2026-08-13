package ar.com.benteveo.backend.features.product.photos;

public record PhotoResponse(
        Long id,
        String url,
        String publicId,
        String caption,
        Integer sortOrder,
        Boolean isPrimary
) {}