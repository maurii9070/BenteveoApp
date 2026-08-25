package ar.com.benteveo.backend.features.product.photos.deletephoto;

import ar.com.benteveo.backend.infrastructure.storage.StorageServiceInterface;
import ar.com.benteveo.backend.repositories.ProductPhotoRepository;
import ar.com.benteveo.backend.repositories.ProductRepository;
import ar.com.benteveo.backend.shared.config.security.UserPrincipal;
import ar.com.benteveo.backend.shared.exception.ForbiddenException;
import ar.com.benteveo.backend.shared.exception.PhotoNotFoundException;
import ar.com.benteveo.backend.shared.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeletePhotoService {

    private final ProductRepository productRepository;
    private final ProductPhotoRepository productPhotoRepository;
    private final StorageServiceInterface storageService;

    public DeletePhotoService(
            ProductRepository productRepository,
            ProductPhotoRepository productPhotoRepository,
            StorageServiceInterface storageService
    ) {
        this.productRepository = productRepository;
        this.productPhotoRepository = productPhotoRepository;
        this.storageService = storageService;
    }

    @Transactional
    public void execute(UUID productId, String publicId, UserPrincipal principal) {
        var product = productRepository.findByIdAndDeletedAtIsNull(productId)
                .orElseThrow(ProductNotFoundException::new);

        if (!principal.isOwnerOrAdmin(product.getOwner().getId())) {
            throw new ForbiddenException();
        }

        var photo = productPhotoRepository.findByPublicIdAndProductId(publicId, productId)
                .orElseThrow(PhotoNotFoundException::new);

        storageService.deleteFile(publicId);

        productPhotoRepository.delete(photo);
        productPhotoRepository.flush();

        var remainingPhotos = productPhotoRepository.findAllByProductIdOrderBySortOrderAsc(productId);
        int sortOrder = 0;
        for (var remaining : remainingPhotos) {
            remaining.setSortOrder(sortOrder++);
        }
        productPhotoRepository.saveAll(remainingPhotos);
    }
}