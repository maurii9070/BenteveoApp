package ar.com.benteveo.backend.features.product.listByUser;

import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.features.product.ProductResponseMapper;
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
    private final ProductResponseMapper productResponseMapper;

    public ListProductsService(
            ProductRepository productRepository,
            UserRepository userRepository,
            ProductResponseMapper productResponseMapper
    ) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productResponseMapper = productResponseMapper;
    }

    public List<ProductResponse> execute(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return productRepository.findAllByOwnerIdAndDeletedAtIsNull(userId).stream()
                .map(productResponseMapper::toResponse)
                .toList();
    }
}