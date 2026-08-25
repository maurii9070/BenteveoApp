package ar.com.benteveo.backend.shared.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("No tienes permisos para realizar esta acción");
    }
}