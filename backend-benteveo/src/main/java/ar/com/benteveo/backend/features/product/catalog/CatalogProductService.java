package ar.com.benteveo.backend.features.product.catalog;

import ar.com.benteveo.backend.enums.ProductStatus;
import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.features.product.ProductResponseMapper;
import ar.com.benteveo.backend.repositories.ProductRepository;
import ar.com.benteveo.backend.repositories.UserRepository;
import ar.com.benteveo.backend.shared.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CatalogProductService {

    private static final ProductStatus DEFAULT_STATUS = ProductStatus.PUBLISHED;

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductResponseMapper productResponseMapper;

    public CatalogProductService(
            ProductRepository productRepository,
            UserRepository userRepository,
            ProductResponseMapper productResponseMapper
    ) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productResponseMapper = productResponseMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> execute(UUID ownerId, ProductStatus status) {
        // Con dueño explícito se listan los productos del usuario (sin filtro de status)
        if (ownerId != null) {
            userRepository.findById(ownerId)
                    .orElseThrow(UserNotFoundException::new);

            return productRepository.findAllByOwnerIdAndDeletedAtIsNull(ownerId).stream()
                    .map(productResponseMapper::toResponse)
                    .toList();
        }

        // Sin dueño: catálogo público con productos activos, filtrado por status (por defecto PUBLISHED)
        ProductStatus resolved = (status == null) ? DEFAULT_STATUS : status;

        return productRepository.findAllByDeletedAtIsNullAndIsActiveTrueAndStatus(resolved).stream()
                .map(productResponseMapper::toResponse)
                .toList();
    }
}