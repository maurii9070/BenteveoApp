package ar.com.benteveo.backend.repositories;

import ar.com.benteveo.backend.entities.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {

    Optional<ProductPhoto> findByPublicIdAndProductId(String publicId, UUID productId);

    List<ProductPhoto> findAllByProductIdOrderBySortOrderAsc(UUID productId);
}