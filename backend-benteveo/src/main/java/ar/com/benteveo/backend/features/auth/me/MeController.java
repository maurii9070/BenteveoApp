package ar.com.benteveo.backend.features.auth.me;

import ar.com.benteveo.backend.shared.config.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ar.com.benteveo.backend.shared.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Autenticación y gestión de sesión del usuario")
@RestController
@RequestMapping("/api/v1/auth")
public class MeController {

    private final MeService meService;

    public MeController(MeService meService) {
        this.meService = meService;
    }

    @Operation(summary = "Obtener usuario autenticado", description = "Devuelve la información del usuario correspondiente al token JWT enviado en la cabecera Authorization.")
    @GetMapping("/me")
    public ApiResponse<MeResponse> me(@AuthenticationPrincipal UserPrincipal principal) {
        var response = meService.execute(principal.getId());
        return ApiResponse.success("Datos del usuario autenticado", response);
    }
}
