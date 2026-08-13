package ar.com.benteveo.backend.features.product.create;

import ar.com.benteveo.backend.entities.Product;
import ar.com.benteveo.backend.enums.ProductStatus;
import ar.com.benteveo.backend.repositories.CategoryRepository;
import ar.com.benteveo.backend.repositories.ProductRepository;
import ar.com.benteveo.backend.repositories.UserRepository;
import ar.com.benteveo.backend.shared.exception.CategoryNotFoundException;
import ar.com.benteveo.backend.shared.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CreateProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CreateProductResponse execute(UUID userId, CreateProductRequest request) {
        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        var owner = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        var product = Product.builder()
                .title(request.title())
                .description(request.description())
                .priceDay(request.priceDay())
                .priceWeek(request.priceWeek())
                .priceMonth(request.priceMonth())
                .deposit(request.deposit())
                .category(category)
                .owner(owner)
                .status(ProductStatus.DRAFT)
                .isActive(true)
                .build();

        var saved = productRepository.save(product);

        return new CreateProductResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getStatus().name(),
                saved.getCreatedAt()
        );
    }
}
