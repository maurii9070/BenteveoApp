package ar.com.benteveo.backend.features.auth.register;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Autenticación y gestión de sesión del usuario")
@RestController
@RequestMapping("/api/v1/auth")
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Operation(summary = "Registrar usuario", description = "Crea una nueva cuenta de usuario con email, contraseña y datos personales. Devuelve los datos del usuario creado.")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        var response = registerService.execute(request);
        return ApiResponse.success("Usuario registrado correctamente", response);
    }

}
