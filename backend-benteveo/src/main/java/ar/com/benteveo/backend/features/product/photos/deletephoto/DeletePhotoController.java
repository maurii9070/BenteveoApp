package ar.com.benteveo.backend.features.product.photos.deletephoto;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class DeletePhotoController {

    private final DeletePhotoService deletePhotoService;

    public DeletePhotoController(DeletePhotoService deletePhotoService) {
        this.deletePhotoService = deletePhotoService;
    }

    @DeleteMapping("/{productId}/photos/{publicId:.+}")
    public ApiResponse<Void> delete(
            @PathVariable UUID productId,
            @PathVariable String publicId
    ) {
        deletePhotoService.execute(productId, publicId);
        return ApiResponse.success("Foto eliminada correctamente", null);
    }
}