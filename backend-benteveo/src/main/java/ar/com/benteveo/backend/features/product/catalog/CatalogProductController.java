package ar.com.benteveo.backend.features.product.catalog;

import ar.com.benteveo.backend.enums.ProductStatus;
import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Products", description = "Gestión del catálogo de productos")
@RestController
@RequestMapping("/api/v1/products")
public class CatalogProductController {

    private final CatalogProductService catalogProductService;

    public CatalogProductController(CatalogProductService catalogProductService) {
        this.catalogProductService = catalogProductService;
    }

    @Operation(summary = "Obtener productos",
            description = "Devuelve productos. Si se indica un dueño (owner) se listan los productos de ese usuario. "
                    + "De lo contrario, devuelve el catálogo público con productos activos, filtrado por status "
                    + "(por defecto PUBLISHED). Al filtrar por dueño, el status no aplica.")
    @GetMapping
    public ApiResponse<List<ProductResponse>> list(
            @RequestParam(required = false)
            @Parameter(description = "ID del usuario dueño. Si se envía, devuelve los productos de ese usuario.")
            UUID owner,

            @RequestParam(required = false)
            @Parameter(description = "Estado del producto para el catálogo público. Valores: DRAFT, PUBLISHED, PAUSED, ARCHIVED.")
            ProductStatus status
    ) {
        var response = catalogProductService.execute(owner, status);
        return ApiResponse.success("Productos obtenidos correctamente", response);
    }
}