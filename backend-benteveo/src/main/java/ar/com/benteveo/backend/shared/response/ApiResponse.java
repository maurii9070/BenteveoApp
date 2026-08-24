package ar.com.benteveo.backend.shared.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.ZoneId;

public record ApiResponse<T>(
        @Schema(example = "true")
        boolean success,

        @Schema(example = "Operación realizada correctamente")
        String message,

        T data,

        @Schema(example = "2026-08-23T20:00:00")
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now(ZoneId.systemDefault()));
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now(ZoneId.systemDefault()));
    }
}
