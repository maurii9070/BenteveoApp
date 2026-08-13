package ar.com.benteveo.backend.features.product.photos.addphotos;

import ar.com.benteveo.backend.entities.ProductPhoto;
import ar.com.benteveo.backend.features.product.photos.PhotoResponse;
import ar.com.benteveo.backend.infrastructure.storage.StorageResult;
import ar.com.benteveo.backend.infrastructure.storage.StorageServiceInterface;
import ar.com.benteveo.backend.repositories.ProductPhotoRepository;
import ar.com.benteveo.backend.repositories.ProductRepository;
import ar.com.benteveo.backend.shared.exception.FileValidationException;
import ar.com.benteveo.backend.shared.exception.ProductNotFoundException;
import ar.com.benteveo.backend.shared.validation.ImageFileValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class AddPhotosService {

    private final ProductRepository productRepository;
    private final ProductPhotoRepository productPhotoRepository;
    private final StorageServiceInterface storageService;
    private final ImageFileValidator imageFileValidator;

    public AddPhotosService(
            ProductRepository productRepository,
            ProductPhotoRepository productPhotoRepository,
            StorageServiceInterface storageService,
            ImageFileValidator imageFileValidator
    ) {
        this.productRepository = productRepository;
        this.productPhotoRepository = productPhotoRepository;
        this.storageService = storageService;
        this.imageFileValidator = imageFileValidator;
    }

    @Transactional
    public List<PhotoResponse> execute(UUID productId, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new FileValidationException("Debe enviar al menos un archivo");
        }

        files.forEach(imageFileValidator::validate);

        var product = productRepository.findByIdAndDeletedAtIsNull(productId)
                .orElseThrow(ProductNotFoundException::new);

        int currentCount = product.getPhotos() != null ? product.getPhotos().size() : 0;

        List<ProductPhoto> savedPhotos = new java.util.ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            StorageResult uploaded = storageService.uploadFile(files.get(i), "products");

            var photo = ProductPhoto.builder()
                    .url(uploaded.url())
                    .publicId(uploaded.publicId())
                    .sortOrder(currentCount + i)
                    .isPrimary(currentCount == 0 && i == 0)
                    .product(product)
                    .build();

            savedPhotos.add(productPhotoRepository.save(photo));
        }

        return savedPhotos.stream()
                .map(photo -> new PhotoResponse(
                        photo.getId(),
                        photo.getUrl(),
                        photo.getPublicId(),
                        photo.getCaption(),
                        photo.getSortOrder(),
                        photo.getIsPrimary()
                ))
                .toList();
    }
}