package ar.com.benteveo.backend.shared.exception;

public class FileValidationException extends RuntimeException {

    public FileValidationException(String message) {
        super(message);
    }
}
