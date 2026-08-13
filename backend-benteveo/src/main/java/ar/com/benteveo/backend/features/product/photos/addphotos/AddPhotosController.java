package ar.com.benteveo.backend.features.product.photos.addphotos;

import ar.com.benteveo.backend.features.product.photos.PhotoResponse;
import ar.com.benteveo.backend.shared.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class AddPhotosController {

    private final AddPhotosService addPhotosService;

    public AddPhotosController(AddPhotosService addPhotosService) {
        this.addPhotosService = addPhotosService;
    }

    @PostMapping(value = "/{productId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<List<PhotoResponse>> addPhotos(
            @PathVariable UUID productId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        var response = addPhotosService.execute(productId, files);
        return ApiResponse.success("Fotos agregadas correctamente", response);
    }
}