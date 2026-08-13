package ar.com.benteveo.backend.shared.validation;

import ar.com.benteveo.backend.shared.exception.FileValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImageFileValidator {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/webp",
            "image/gif"
    );

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileValidationException("El archivo es obligatorio");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileValidationException("El archivo supera el tamaño maximo de 5 MB");
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new FileValidationException(
                    "Tipo de archivo no permitido. Solo se permiten: JPEG, PNG, WebP, GIF"
            );
        }
    }
}
