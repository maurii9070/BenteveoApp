package ar.com.benteveo.backend.features.product.getProduct;

import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.features.product.ProductResponseMapper;
import ar.com.benteveo.backend.repositories.ProductRepository;
import ar.com.benteveo.backend.shared.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetProductService {

    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;

    public GetProductService(ProductRepository productRepository, ProductResponseMapper productResponseMapper) {
        this.productRepository = productRepository;
        this.productResponseMapper = productResponseMapper;
    }

    public ProductResponse execute(UUID id) {
        var product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        return productResponseMapper.toResponse(product);
    }
}
