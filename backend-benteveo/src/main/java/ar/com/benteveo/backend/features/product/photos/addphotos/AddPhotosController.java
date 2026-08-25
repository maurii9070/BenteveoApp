package ar.com.benteveo.backend.features.product.photos.addphotos;

import ar.com.benteveo.backend.features.product.photos.PhotoResponse;
import ar.com.benteveo.backend.shared.config.security.UserPrincipal;
import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "Products", description = "Gestión del catálogo de productos")
@RestController
@RequestMapping("/api/v1/products")
public class AddPhotosController {

    private final AddPhotosService addPhotosService;

    public AddPhotosController(AddPhotosService addPhotosService) {
        this.addPhotosService = addPhotosService;
    }

    @Operation(summary = "Agregar fotos a un producto", description = "Sube una o varias imágenes (multipart/form-data) y las asocia al producto indicado. Solo el dueño del producto o un rol ADMIN pueden hacerlo.")
    @PostMapping(value = "/{productId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<List<PhotoResponse>> addPhotos(
            @PathVariable UUID productId,
            @Parameter(
                    name = "files",
                    description = "Archivos de imagen (JPEG, PNG, WebP, GIF). Máximo 5 MB por archivo.",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "array", implementation = MultipartFile.class)
                    )
            )
            @RequestParam("files") List<MultipartFile> files,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        var response = addPhotosService.execute(productId, files, principal);
        return ApiResponse.success("Fotos agregadas correctamente", response);
    }
}