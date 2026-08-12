package ar.com.benteveo.backend.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageServiceInterface {

    StorageResult uploadFile(MultipartFile file, String folder);

    void deleteFile(String publicId);
}
