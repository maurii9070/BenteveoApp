package ar.com.benteveo.backend.features.product.delete;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class DeleteProductController {

    private final DeleteProductService deleteProductService;

    public DeleteProductController(DeleteProductService deleteProductService) {
        this.deleteProductService = deleteProductService;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        deleteProductService.execute(id);
        return ApiResponse.success("Producto eliminado correctamente", null);
    }
}
