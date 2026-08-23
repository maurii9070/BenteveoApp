package ar.com.benteveo.backend.features.product.listByUser;

import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Products", description = "Gestión del catálogo de productos")
@RestController
@RequestMapping("/api/v1")
public class ListProductsController {

    private final ListProductsService listProductsService;

    public ListProductsController(ListProductsService listProductsService) {
        this.listProductsService = listProductsService;
    }

    @Operation(summary = "Listar productos de un usuario", description = "Devuelve la lista de productos pertenecientes al usuario indicado por su ID en la ruta.")
    @GetMapping("/users/{userId}/products")
    public ApiResponse<List<ProductResponse>> list(@PathVariable UUID userId) {
        var response = listProductsService.execute(userId);
        return ApiResponse.success("Productos obtenidos correctamente", response);
    }
}