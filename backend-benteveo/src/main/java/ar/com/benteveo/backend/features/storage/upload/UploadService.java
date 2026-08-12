package ar.com.benteveo.backend.features.storage.upload;

import ar.com.benteveo.backend.infrastructure.storage.StorageServiceInterface;
import ar.com.benteveo.backend.shared.exception.FileValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UploadService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/webp",
            "image/gif"
    );

    private final StorageServiceInterface storageService;

    public UploadService(StorageServiceInterface storageService) {
        this.storageService = storageService;
    }

    private static final String DEFAULT_FOLDER = "uploads";

    public UploadResponse execute(MultipartFile file) {
        validateFile(file);
        var result = storageService.uploadFile(file, DEFAULT_FOLDER);
        return new UploadResponse(result.url(), result.publicId());
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileValidationException("El archivo es obligatorio");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileValidationException(
                    "El archivo supera el tamaño maximo de 5 MB"
            );
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new FileValidationException(
                    "Tipo de archivo no permitido. Solo se permiten: JPEG, PNG, WebP, GIF"
            );
        }
    }
}
