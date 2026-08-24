package ar.com.benteveo.backend.features.product.create;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Products", description = "Gestión del catálogo de productos")
@RestController
@RequestMapping("/api/v1")
public class CreateProductController {

    private final CreateProductService createProductService;

    public CreateProductController(CreateProductService createProductService) {
        this.createProductService = createProductService;
    }

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto asociado a un usuario (dueño). Requiere los datos del producto y el ID del dueño en la ruta.")
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
