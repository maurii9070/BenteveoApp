package ar.com.benteveo.backend.features.storage.delete;

import ar.com.benteveo.backend.infrastructure.storage.StorageServiceInterface;
import org.springframework.stereotype.Service;

@Service
public class DeleteService {

    private final StorageServiceInterface storageService;

    public DeleteService(StorageServiceInterface storageService) {
        this.storageService = storageService;
    }

    public void execute(DeleteRequest request) {
        storageService.deleteFile(request.publicId());
    }
}
