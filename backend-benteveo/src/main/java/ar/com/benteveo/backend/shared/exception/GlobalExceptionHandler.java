package ar.com.benteveo.backend.shared.exception;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura excepciones de recursos duplicados (Email o DNI ya registrados) -> HTTP 409 Conflict
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura errores de validación de los DTOs (@NotBlank, @Email, @Size)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return new ApiResponse<>(false, "Error de validación en los campos", errors, java.time.LocalDateTime.now());
    }

    // Captura restricciones únicas no atajadas a nivel DB -> HTTP 409 Conflict
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ApiResponse.error("Ya existe un registro con los datos ingresados en la base de datos.");
    }

    // Captura errores genéricos o no controlados de la aplicación -> HTTP 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleGlobalException(Exception ex) {
        return ApiResponse.error("Ocurrió un error interno en el servidor. Intente más tarde.");
    }
}
