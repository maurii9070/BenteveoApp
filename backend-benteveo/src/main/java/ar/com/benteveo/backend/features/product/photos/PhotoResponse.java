package ar.com.benteveo.backend.features.product.photos;

import io.swagger.v3.oas.annotations.media.Schema;

public record PhotoResponse(
        @Schema(example = "1")
        Long id,

        @Schema(example = "https://res.cloudinary.com/demo/image/upload/v1/products/abc123.jpg")
        String url,

        @Schema(example = "products/abc123")
        String publicId,

        @Schema(example = "Vista frontal del producto")
        String caption,

        @Schema(example = "1")
        Integer sortOrder,

        @Schema(example = "true")
        Boolean isPrimary
) {}
