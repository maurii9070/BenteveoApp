package ar.com.benteveo.backend.features.product.create;

import ar.com.benteveo.backend.shared.config.security.UserPrincipal;
import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Products", description = "Gestión del catálogo de productos")
@RestController
@RequestMapping("/api/v1")
public class CreateProductController {

    private final CreateProductService createProductService;

    public CreateProductController(CreateProductService createProductService) {
        this.createProductService = createProductService;
    }

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto asociado al usuario autenticado (dueño tomado del token JWT).")
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateProductResponse> create(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody CreateProductRequest request
    ) {
        var response = createProductService.execute(principal, request);
        return ApiResponse.success("Producto creado correctamente", response);
    }

}
