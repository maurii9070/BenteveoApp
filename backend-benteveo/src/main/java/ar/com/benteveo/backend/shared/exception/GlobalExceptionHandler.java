package ar.com.benteveo.backend.shared.exception;

import ar.com.benteveo.backend.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String MEDIA_JSON = "application/json";

    private static final String DESC_400 = "Solicitud inválida (validación de campos, JSON malformado o archivo inválido)";
    private static final String EXAMPLE_400 = "{\"success\":false,\"message\":\"Error de validación en los campos\",\"data\":{\"email\":\"El email es obligatorio\"},\"timestamp\":\"2026-08-23T20:00:00\"}";

    private static final String DESC_401 = "No autenticado o credenciales inválidas";
    private static final String EXAMPLE_401 = "{\"success\":false,\"message\":\"Credenciales inválidas o token ausente\",\"data\":null,\"timestamp\":\"2026-08-23T20:00:00\"}";

    private static final String DESC_404 = "Recurso no encontrado";
    private static final String EXAMPLE_404 = "{\"success\":false,\"message\":\"Recurso no encontrado\",\"data\":null,\"timestamp\":\"2026-08-23T20:00:00\"}";

    private static final String DESC_409 = "Conflicto: el recurso ya existe (email o DNI duplicado)";
    private static final String EXAMPLE_409 = "{\"success\":false,\"message\":\"El email ya se encuentra registrado\",\"data\":null,\"timestamp\":\"2026-08-23T20:00:00\"}";

    private static final String DESC_413 = "El archivo supera el tamaño máximo permitido (5 MB)";
    private static final String EXAMPLE_413 = "{\"success\":false,\"message\":\"El archivo supera el tamaño máximo permitido (5 MB)\",\"data\":null,\"timestamp\":\"2026-08-23T20:00:00\"}";

    private static final String DESC_500 = "Error interno del servidor";
    private static final String EXAMPLE_500 = "{\"success\":false,\"message\":\"Ocurrió un error interno en el servidor. Intente más tarde.\",\"data\":null,\"timestamp\":\"2026-08-23T20:00:00\"}";

    private static final String DESC_502 = "Error del servicio de almacenamiento (Cloudinary)";
    private static final String EXAMPLE_502 = "{\"success\":false,\"message\":\"Error al comunicarse con el servicio de almacenamiento\",\"data\":null,\"timestamp\":\"2026-08-23T20:00:00\"}";

    // Captura excepciones de recursos duplicados (Email o DNI ya registrados) -> HTTP 409 Conflict
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409",
            description = DESC_409,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_409)
            )
    )
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura errores de validación de los DTOs (@NotBlank, @Email, @Size)
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = DESC_400,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_400)
            )
    )
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
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409",
            description = DESC_409,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_409)
            )
    )
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ApiResponse.error("Ya existe un registro con los datos ingresados en la base de datos.");
    }

    // Captura errores genéricos o no controlados de la aplicación -> HTTP 500 Internal Server Error
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "500",
            description = DESC_500,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_500)
            )
    )
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleGlobalException(Exception ex) {
        return ApiResponse.error("Ocurrió un error interno en el servidor. Intente más tarde.");
    }

    // Captura errores de login (Credenciales incorrectas) -> HTTP 401 Unauthorized
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = DESC_401,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_401)
            )
    )
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura errores de validación de archivos (tamaño, tipo MIME) -> HTTP 400 Bad Request
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = DESC_400,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_400)
            )
    )
    @ExceptionHandler(FileValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleFileValidation(FileValidationException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura errores del servicio de almacenamiento (Cloudinary) -> HTTP 502 Bad Gateway
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "502",
            description = DESC_502,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_502)
            )
    )
    @ExceptionHandler(StorageException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiResponse<Void> handleStorageException(StorageException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura cuando no se encuentra un producto -> HTTP 404 Not Found
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = DESC_404,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_404)
            )
    )
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleProductNotFound(ProductNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura cuando no se encuentra una categoria -> HTTP 404 Not Found
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = DESC_404,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_404)
            )
    )
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleCategoryNotFound(CategoryNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura cuando no se encuentra un usuario -> HTTP 404 Not Found
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = DESC_404,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_404)
            )
    )
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleUserNotFound(UserNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura cuando no se encuentra una foto de producto -> HTTP 404 Not Found
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = DESC_404,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_404)
            )
    )
    @ExceptionHandler(PhotoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handlePhotoNotFound(PhotoNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura archivos que superan el tamaño máximo configurado -> HTTP 413 Payload Too Large
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "413",
            description = DESC_413,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_413)
            )
    )
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.CONTENT_TOO_LARGE)
    public ApiResponse<Void> handleMaxUploadSize(MaxUploadSizeExceededException ex) {
        return ApiResponse.error("El archivo supera el tamaño maximo permitido (5 MB)");
    }

    // Captura problemas generales de multipart (parte faltante, request malformado) -> HTTP 400 Bad Request
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = DESC_400,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_400)
            )
    )
    @ExceptionHandler({MissingServletRequestPartException.class, MissingServletRequestParameterException.class, MultipartException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMultipartErrors(Exception ex) {
        return ApiResponse.error("Solicitud multipart invalida. Verifique los campos del formulario");
    }

    // Captura JSON malformado o body no legible -> HTTP 400 Bad Request
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = DESC_400,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_400)
            )
    )
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleNotReadable(HttpMessageNotReadableException ex) {
        return ApiResponse.error("El cuerpo de la solicitud no es valido");
    }

    // Captura UUID o tipo de dato invalido en los parametros de la URL -> HTTP 400 Bad Request
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = DESC_400,
            content = @Content(
                    mediaType = MEDIA_JSON,
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = EXAMPLE_400)
            )
    )
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ApiResponse.error("Parametro invalido en la solicitud: " + ex.getName());
    }
}
