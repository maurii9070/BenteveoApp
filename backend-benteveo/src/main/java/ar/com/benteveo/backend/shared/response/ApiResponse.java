package ar.com.benteveo.backend.shared.response;

import java.time.LocalDateTime;
import java.time.ZoneId;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now(ZoneId.systemDefault()));
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now(ZoneId.systemDefault()));
    }
}