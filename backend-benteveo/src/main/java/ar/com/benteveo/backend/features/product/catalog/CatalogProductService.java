package ar.com.benteveo.backend.features.product.catalog;

import ar.com.benteveo.backend.enums.ProductStatus;
import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.features.product.ProductResponseMapper;
import ar.com.benteveo.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatalogProductService {

    private static final ProductStatus DEFAULT_STATUS = ProductStatus.PUBLISHED;

    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;

    public CatalogProductService(ProductRepository productRepository, ProductResponseMapper productResponseMapper) {
        this.productRepository = productRepository;
        this.productResponseMapper = productResponseMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> execute(ProductStatus status) {
        ProductStatus resolved = (status == null) ? DEFAULT_STATUS : status;

        return productRepository.findAllByDeletedAtIsNullAndIsActiveTrueAndStatus(resolved).stream()
                .map(productResponseMapper::toResponse)
                .toList();
    }
}