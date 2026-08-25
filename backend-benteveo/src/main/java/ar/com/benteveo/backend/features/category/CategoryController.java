package ar.com.benteveo.backend.features.category;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Categories", description = "Gestión de categorías del catálogo")
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CatalogCategoryService catalogCategoryService;

    public CategoryController(CatalogCategoryService catalogCategoryService) {
        this.catalogCategoryService = catalogCategoryService;
    }

    @Operation(summary = "Obtener categorías", description = "Devuelve todas las categorías disponibles para el catálogo.")
    @GetMapping
    public ApiResponse<List<CategoryResponse>> list() {
        var response = catalogCategoryService.execute();
        return ApiResponse.success("Categorías obtenidas correctamente", response);
    }
}