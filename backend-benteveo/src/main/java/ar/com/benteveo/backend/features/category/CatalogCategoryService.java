package ar.com.benteveo.backend.features.category;

import ar.com.benteveo.backend.entities.Category;
import ar.com.benteveo.backend.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatalogCategoryService {

    private final CategoryRepository categoryRepository;

    public CatalogCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> execute() {
        return categoryRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getSlug()
        );
    }
}