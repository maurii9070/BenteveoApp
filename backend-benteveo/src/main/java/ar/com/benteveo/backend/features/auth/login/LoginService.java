package ar.com.benteveo.backend.features.auth.login;

import ar.com.benteveo.backend.shared.config.security.JwtService;
import ar.com.benteveo.backend.shared.config.security.UserPrincipal;
import ar.com.benteveo.backend.shared.exception.InvalidCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginService(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse execute(LoginRequest request) {
        // 1. Spring Security valida credenciales contra la base de datos
        //    (CustomUserDetailsService + PasswordEncoder + verificación de cuenta activa)
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (BadCredentialsException | DisabledException ex) {
            throw new InvalidCredentialsException();
        }

        // 2. El principal autenticado ya es nuestro UserPrincipal (UserDetails)
        var principal = (UserPrincipal) authentication.getPrincipal();

        // 3. Se obtiene el rol desde las authorities (ROLE_USER -> USER)
        var roleName = principal.getAuthorities().stream()
                .findFirst()
                .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                .orElse("USER");

        // 4. Generar el Token JWT
        var token = jwtService.generateToken(principal.getId(), principal.getUsername(), roleName);

        return new LoginResponse(token);
    }
}
