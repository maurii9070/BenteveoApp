package ar.com.benteveo.backend.shared.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Usuario no encontrado");
    }
}
