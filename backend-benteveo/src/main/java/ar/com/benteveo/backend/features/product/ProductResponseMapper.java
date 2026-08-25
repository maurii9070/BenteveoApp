package ar.com.benteveo.backend.features.product;

import ar.com.benteveo.backend.entities.Product;
import ar.com.benteveo.backend.features.product.photos.PhotoResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Centraliza la construcción de {@link ProductResponse} para evitar duplicar
 * el mapeo entre la entidad y el DTO de salida.
 */
@Component
public class ProductResponseMapper {

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPriceDay(),
                product.getPriceWeek(),
                product.getPriceMonth(),
                product.getDeposit(),
                product.getIsActive(),
                product.getStatus().name(),
                product.getRatingAvg(),
                product.getRatingCount(),
                product.getCategory().getName(),
                product.getOwner().getId(),
                product.getPhotos() != null
                        ? product.getPhotos().stream()
                                .map(photo -> new PhotoResponse(
                                        photo.getId(),
                                        photo.getUrl(),
                                        photo.getPublicId(),
                                        photo.getCaption(),
                                        photo.getSortOrder(),
                                        photo.getIsPrimary()
                                ))
                                .toList()
                        : List.of(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}