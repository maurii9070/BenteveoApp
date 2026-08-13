package ar.com.benteveo.backend.features.product.list;

import ar.com.benteveo.backend.features.product.ProductResponse;
import ar.com.benteveo.backend.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ListProductsController {

    private final ListProductsService listProductsService;

    public ListProductsController(ListProductsService listProductsService) {
        this.listProductsService = listProductsService;
    }

    @GetMapping("/users/{userId}/products")
    public ApiResponse<List<ProductResponse>> list(@PathVariable UUID userId) {
        var response = listProductsService.execute(userId);
        return ApiResponse.success("Productos obtenidos correctamente", response);
    }
}