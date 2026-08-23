package ar.com.benteveo.backend.features.product.update;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente identificado por su ID. Todos los campos son opcionales.")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable UUID id, @RequestBody UpdateProductRequest request) {
        updateProductService.execute(id, request);
        return ApiResponse.success("Producto actualizado correctamente", null);
    }
}
