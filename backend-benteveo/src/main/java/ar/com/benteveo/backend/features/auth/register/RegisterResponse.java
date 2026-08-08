package ar.com.benteveo.backend.features.auth.register;

import java.util.UUID;

public record RegisterResponse(
        UUID id,
        String email,
        String name
) {}
