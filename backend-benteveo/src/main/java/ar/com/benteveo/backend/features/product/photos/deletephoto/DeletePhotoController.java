package ar.com.benteveo.backend.features.product.photos.deletephoto;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Products", description = "Gestión del catálogo de productos")
@RestController
@RequestMapping("/api/v1/products")
public class DeletePhotoController {

    private final DeletePhotoService deletePhotoService;

    public DeletePhotoController(DeletePhotoService deletePhotoService) {
        this.deletePhotoService = deletePhotoService;
    }

    @Operation(summary = "Eliminar foto de un producto", description = "Elimina una foto de un producto a partir del ID público de la imagen en Cloudinary.")
    @DeleteMapping("/{productId}/photos/{publicId:.+}")
    public ApiResponse<Void> delete(
            @PathVariable UUID productId,
            @PathVariable String publicId
    ) {
        deletePhotoService.execute(productId, publicId);
        return ApiResponse.success("Foto eliminada correctamente", null);
    }
}