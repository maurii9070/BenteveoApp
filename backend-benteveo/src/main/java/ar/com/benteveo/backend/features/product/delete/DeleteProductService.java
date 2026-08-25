package ar.com.benteveo.backend.features.product.delete;

import ar.com.benteveo.backend.repositories.ProductRepository;
import ar.com.benteveo.backend.shared.config.security.UserPrincipal;
import ar.com.benteveo.backend.shared.exception.ForbiddenException;
import ar.com.benteveo.backend.shared.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DeleteProductService {

    private final ProductRepository productRepository;

    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void execute(UUID id, UserPrincipal principal) {
        var product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(ProductNotFoundException::new);

        if (!principal.isOwnerOrAdmin(product.getOwner().getId())) {
            throw new ForbiddenException();
        }

        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }
}
