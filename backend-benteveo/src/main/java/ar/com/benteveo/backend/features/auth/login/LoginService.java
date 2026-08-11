package ar.com.benteveo.backend.features.auth.login;

import ar.com.benteveo.backend.repositories.UserRepository;
import ar.com.benteveo.backend.shared.config.security.JwtService;
import ar.com.benteveo.backend.shared.exception.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse execute(LoginRequest request) {
        // 1. Buscar usuario por email
        var user = userRepository.findByEmail(request.email())
                                 .orElseThrow(InvalidCredentialsException::new);

        // 2. Validar contraseña cifrada
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // 3. Validar si la cuenta está activa
        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new InvalidCredentialsException();
        }

        // 4. Generar el Token JWT
        var roleName = user.getRoles().isEmpty() ? "USER" : user.getRoles().getFirst().name();
        var token = jwtService.generateToken(user.getId(), user.getEmail(), roleName);

        return new LoginResponse(token);
    }
}
