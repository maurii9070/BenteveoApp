package ar.com.benteveo.backend.infrastructure.storage;

import ar.com.benteveo.backend.shared.exception.StorageException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryStorageService implements StorageServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(CloudinaryStorageService.class);

    private final Cloudinary cloudinary;

    public CloudinaryStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    @SuppressWarnings("unchecked")
    public StorageResult uploadFile(MultipartFile file, String folder) {
        try {
            Map<String, Object> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "auto"
                    )
            );
            String url = result.get("secure_url").toString();
            String publicId = result.get("public_id").toString();
            return new StorageResult(url, publicId);
        } catch (Exception e) {
            log.error("Error al subir archivo a Cloudinary: {}", e.getMessage(), e);
            throw new StorageException("Error al subir el archivo al almacenamiento", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteFile(String publicId) {
        try {
            Map<String, Object> result = cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap("resource_type", "image")
            );
            String status = result.get("result").toString();
            if (!"ok".equals(status)) {
                log.warn("Cloudinary no encontro el archivo con publicId: {}", publicId);
                throw new StorageException("No se encontro el archivo con el identificador proporcionado");
            }
        } catch (StorageException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar archivo de Cloudinary: {}", e.getMessage(), e);
            throw new StorageException("Error al eliminar el archivo del almacenamiento", e);
        }
    }
}