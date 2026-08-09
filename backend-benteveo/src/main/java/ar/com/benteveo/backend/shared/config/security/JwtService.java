package ar.com.benteveo.backend.shared.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs
    ) {
        // Genera la clave criptográfica HMAC a partir del string en application.properties / .env
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Genera un token JWT firmado que incluye el email como Subject,
     * el ID del usuario y su Rol como Claims personalizadas.
     */
    public String generateToken(UUID userId, String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                   .subject(email)
                   .claim("userId", userId.toString())
                   .claim("role", role)
                   .issuedAt(now)
                   .expiration(expiryDate)
                   .signWith(key)
                   .compact();
    }

    /**
     * Extrae el email (Subject) del token JWT.
     */
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Valida que el token tenga una firma correcta y no haya expirado.
     */
    public boolean isTokenValid(String token) {
        try {
            return getClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Desencripta y lee el contenido (Claims) del token usando la clave secreta.
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                   .verifyWith(key)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }
}
