package ar.com.benteveo.backend.features.product.update;

import ar.com.benteveo.backend.enums.ProductStatus;
import ar.com.benteveo.backend.repositories.CategoryRepository;
import ar.com.benteveo.backend.repositories.ProductRepository;
import ar.com.benteveo.backend.shared.config.security.UserPrincipal;
import ar.com.benteveo.backend.shared.exception.CategoryNotFoundException;
import ar.com.benteveo.backend.shared.exception.ForbiddenException;
import ar.com.benteveo.backend.shared.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public UpdateProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void execute(UUID id, UpdateProductRequest request, UserPrincipal principal) {
        var product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(ProductNotFoundException::new);

        if (!principal.isOwnerOrAdmin(product.getOwner().getId())) {
            throw new ForbiddenException();
        }

        if (request.title() != null) {
            product.setTitle(request.title());
        }
        if (request.description() != null) {
            product.setDescription(request.description());
        }
        if (request.priceDay() != null) {
            product.setPriceDay(request.priceDay());
        }
        if (request.priceWeek() != null) {
            product.setPriceWeek(request.priceWeek());
        }
        if (request.priceMonth() != null) {
            product.setPriceMonth(request.priceMonth());
        }
        if (request.deposit() != null) {
            product.setDeposit(request.deposit());
        }
        if (request.isActive() != null) {
            product.setIsActive(request.isActive());
        }
        if (request.status() != null) {
            product.setStatus(ProductStatus.valueOf(request.status()));
        }
        if (request.categoryId() != null) {
            var category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(CategoryNotFoundException::new);
            product.setCategory(category);
        }

        productRepository.save(product);
    }
}
