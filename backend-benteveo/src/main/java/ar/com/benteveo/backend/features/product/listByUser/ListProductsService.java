package ar.com.benteveo.backend.features.product.listByUser;

import ar.com.benteveo.backend.features.product.photos.PhotoResponse;
import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.repositories.ProductRepository;
import ar.com.benteveo.backend.repositories.UserRepository;
import ar.com.benteveo.backend.shared.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListProductsService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ListProductsService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<ProductResponse> execute(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return productRepository.findAllByOwnerIdAndDeletedAtIsNull(userId).stream()
                .map(product -> new ProductResponse(
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
                ))
                .toList();
    }
}