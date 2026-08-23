package ar.com.benteveo.backend.features.auth.me;

import ar.com.benteveo.backend.entities.User;
import ar.com.benteveo.backend.repositories.UserRepository;
import ar.com.benteveo.backend.shared.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MeService {

    private final UserRepository userRepository;

    public MeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public MeResponse execute(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return toResponse(user);
    }

    private MeResponse toResponse(User user) {
        var profile = user.getProfile();
        return new MeResponse(
                user.getId(),
                user.getEmail(),
                profile != null ? profile.getFirstName() : null,
                profile != null ? profile.getLastName() : null,
                user.getRoles().stream()
                        .findFirst()
                        .map(Enum::name)
                        .orElse("USER")
        );
    }
}
