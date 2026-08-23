package ar.com.benteveo.backend.features.auth.me;

import java.util.UUID;

public record MeResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String role
) {}
