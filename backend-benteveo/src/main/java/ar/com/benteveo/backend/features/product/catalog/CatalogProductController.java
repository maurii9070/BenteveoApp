package ar.com.benteveo.backend.features.product.catalog;

import ar.com.benteveo.backend.enums.ProductStatus;
import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Products", description = "Gestión del catálogo de productos")
@RestController
@RequestMapping("/api/v1/products")
public class CatalogProductController {

    private final CatalogProductService catalogProductService;

    public CatalogProductController(CatalogProductService catalogProductService) {
        this.catalogProductService = catalogProductService;
    }

    @Operation(summary = "Catálogo de productos",
            description = "Devuelve los productos activos del catálogo. Por defecto solo los publicados (PUBLISHED); se puede filtrar por status.")
    @GetMapping
    public ApiResponse<List<ProductResponse>> catalog(
            @RequestParam(required = false) ProductStatus status
    ) {
        var response = catalogProductService.execute(status);
        return ApiResponse.success("Catálogo de productos obtenido correctamente", response);
    }
}