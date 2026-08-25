package ar.com.benteveo.backend.features.product.delete;

import ar.com.benteveo.backend.shared.config.security.UserPrincipal;
import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Products", description = "Gestión del catálogo de productos")
@RestController
@RequestMapping("/api/v1/products")
public class DeleteProductController {

    private final DeleteProductService deleteProductService;

    public DeleteProductController(DeleteProductService deleteProductService) {
        this.deleteProductService = deleteProductService;
    }

    @Operation(summary = "Eliminar producto", description = "Elimina lógicamente un producto existente. Solo el dueño del producto o un rol ADMIN pueden hacerlo.")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        deleteProductService.execute(id, principal);
        return ApiResponse.success("Producto eliminado correctamente", null);
    }
}
