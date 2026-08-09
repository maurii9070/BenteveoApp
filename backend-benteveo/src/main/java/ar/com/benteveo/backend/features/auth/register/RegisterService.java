package ar.com.benteveo.backend.features.auth.register;

import ar.com.benteveo.backend.entities.Profile;
import ar.com.benteveo.backend.entities.User;
import ar.com.benteveo.backend.enums.Role;
import ar.com.benteveo.backend.repositories.UserRepository;
import ar.com.benteveo.backend.shared.exception.ResourceAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponse execute(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException("El email ya se encuentra registrado");
        }

        if (userRepository.existsByDni(request.dni())) {
            throw new ResourceAlreadyExistsException("El DNI ya se encuentra registrado");
        }

        var user = User.builder()
                       .email(request.email())
                       .password(passwordEncoder.encode(request.password()))
                       .dni(request.dni())
                       .roles(List.of(Role.USER))
                       .isActive(true)
                       .emailVerified(false)
                       .build();

        var profile = Profile.builder()
                             .firstName(request.firstName())
                             .lastName(request.lastName())
                             .user(user)
                             .build();

        user.setProfile(profile);

        var savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getProfile().getFirstName(),
                savedUser.getProfile().getLastName()
        );
    }
}
