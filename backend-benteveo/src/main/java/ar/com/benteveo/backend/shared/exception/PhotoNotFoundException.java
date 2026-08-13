package ar.com.benteveo.backend.shared.exception;

public class PhotoNotFoundException extends RuntimeException {

    public PhotoNotFoundException() {
        super("Foto no encontrada");
    }
}