package ar.com.benteveo.backend.repositories;

import ar.com.benteveo.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    boolean existsByDni(String dni);

    Optional<User> findByEmail(String email);
}