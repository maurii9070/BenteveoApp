package ar.com.benteveo.backend.repositories;

import ar.com.benteveo.backend.entities.Product;
import ar.com.benteveo.backend.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByDeletedAtIsNull();

    List<Product> findAllByOwnerIdAndDeletedAtIsNull(UUID ownerId);

    Optional<Product> findByIdAndDeletedAtIsNull(UUID id);

    // Catálogo público: solo productos no eliminados y activos
    List<Product> findAllByDeletedAtIsNullAndIsActiveTrue();

    List<Product> findAllByDeletedAtIsNullAndIsActiveTrueAndStatus(ProductStatus status);
}
