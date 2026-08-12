package ar.com.benteveo.backend.features.storage.delete;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/storage")
public class DeleteController {

    private final DeleteService deleteService;

    public DeleteController(DeleteService deleteService) {
        this.deleteService = deleteService;
    }

    @DeleteMapping("/delete")
    public ApiResponse<Void> delete(@Valid @RequestBody DeleteRequest request) {
        deleteService.execute(request);
        return ApiResponse.success("Archivo eliminado correctamente", null);
    }
}
