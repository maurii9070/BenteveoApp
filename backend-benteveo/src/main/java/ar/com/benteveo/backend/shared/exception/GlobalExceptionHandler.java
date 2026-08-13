package ar.com.benteveo.backend.shared.exception;

import ar.com.benteveo.backend.shared.response.ApiResponse;
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

    // Captura errores de login (Credenciales incorrectas) -> HTTP 401 Unauthorized
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura errores de validación de archivos (tamaño, tipo MIME) -> HTTP 400 Bad Request
    @ExceptionHandler(FileValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleFileValidation(FileValidationException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura errores del servicio de almacenamiento (Cloudinary) -> HTTP 502 Bad Gateway
    @ExceptionHandler(StorageException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiResponse<Void> handleStorageException(StorageException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura cuando no se encuentra un producto -> HTTP 404 Not Found
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleProductNotFound(ProductNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura cuando no se encuentra una categoria -> HTTP 404 Not Found
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleCategoryNotFound(CategoryNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura cuando no se encuentra un usuario -> HTTP 404 Not Found
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleUserNotFound(UserNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura cuando no se encuentra una foto de producto -> HTTP 404 Not Found
    @ExceptionHandler(PhotoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handlePhotoNotFound(PhotoNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // Captura archivos que superan el tamaño máximo configurado -> HTTP 413 Payload Too Large
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ApiResponse<Void> handleMaxUploadSize(MaxUploadSizeExceededException ex) {
        return ApiResponse.error("El archivo supera el tamaño maximo permitido (5 MB)");
    }

    // Captura problemas generales de multipart (parte faltante, request malformado) -> HTTP 400 Bad Request
    @ExceptionHandler({MissingServletRequestPartException.class, MissingServletRequestParameterException.class, MultipartException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMultipartErrors(Exception ex) {
        return ApiResponse.error("Solicitud multipart invalida. Verifique los campos del formulario");
    }

    // Captura JSON malformado o body no legible -> HTTP 400 Bad Request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleNotReadable(HttpMessageNotReadableException ex) {
        return ApiResponse.error("El cuerpo de la solicitud no es valido");
    }

    // Captura UUID o tipo de dato invalido en los parametros de la URL -> HTTP 400 Bad Request
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ApiResponse.error("Parametro invalido en la solicitud: " + ex.getName());
    }
}
