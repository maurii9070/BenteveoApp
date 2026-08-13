package ar.com.benteveo.backend.features.product.create;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class CreateProductController {

    private final CreateProductService createProductService;

    public CreateProductController(CreateProductService createProductService) {
        this.createProductService = createProductService;
    }

    @PostMapping("/users/{userId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateProductResponse> create(
            @PathVariable UUID userId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        var response = createProductService.execute(userId, request);
        return ApiResponse.success("Producto creado correctamente", response);
    }
}
