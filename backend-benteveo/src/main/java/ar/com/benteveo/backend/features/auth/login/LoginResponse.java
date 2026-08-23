package ar.com.benteveo.backend.features.auth.login;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzZmE4NWY2NCIsInJvbGUiOiJVU0VSIn0.xxxxx")
        String token
) {
}
