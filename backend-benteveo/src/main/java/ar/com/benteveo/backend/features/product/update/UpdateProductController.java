package ar.com.benteveo.backend.features.product.update;

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
public class UpdateProductController {

    private final UpdateProductService updateProductService;

    public UpdateProductController(UpdateProductService updateProductService) {
        this.updateProductService = updateProductService;
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente. Solo el dueño del producto o un rol ADMIN pueden hacerlo. Todos los campos son opcionales.")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable UUID id,
            @RequestBody UpdateProductRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        updateProductService.execute(id, request, principal);
        return ApiResponse.success("Producto actualizado correctamente", null);
    }
}
