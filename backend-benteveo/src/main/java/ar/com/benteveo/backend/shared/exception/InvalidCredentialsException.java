package ar.com.benteveo.backend.shared.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Credenciales inválidas. Verifique su email y contraseña.");
    }
}
