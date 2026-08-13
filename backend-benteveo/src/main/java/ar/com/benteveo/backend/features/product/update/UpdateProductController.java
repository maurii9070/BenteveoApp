package ar.com.benteveo.backend.features.product.update;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class UpdateProductController {

    private final UpdateProductService updateProductService;

    public UpdateProductController(UpdateProductService updateProductService) {
        this.updateProductService = updateProductService;
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable UUID id, @RequestBody UpdateProductRequest request) {
        updateProductService.execute(id, request);
        return ApiResponse.success("Producto actualizado correctamente", null);
    }
}
