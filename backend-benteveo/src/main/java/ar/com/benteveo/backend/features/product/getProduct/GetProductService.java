package ar.com.benteveo.backend.features.product.getProduct;

import ar.com.benteveo.backend.features.product.photos.PhotoResponse;
import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.repositories.ProductRepository;
import ar.com.benteveo.backend.shared.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetProductService {

    private final ProductRepository productRepository;

    public GetProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse execute(UUID id) {
        var product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

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
                        : java.util.List.of(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
