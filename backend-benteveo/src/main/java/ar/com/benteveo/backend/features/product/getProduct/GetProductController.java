package ar.com.benteveo.backend.features.product.getProduct;

import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class GetProductController {

    private final GetProductService getProductService;

    public GetProductController(GetProductService getProductService) {
        this.getProductService = getProductService;
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> get(@PathVariable UUID id) {
        var response = getProductService.execute(id);
        return ApiResponse.success("Producto obtenido correctamente", response);
    }
}
