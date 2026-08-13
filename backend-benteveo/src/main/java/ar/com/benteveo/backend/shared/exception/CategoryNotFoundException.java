package ar.com.benteveo.backend.shared.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super("Categoria no encontrada");
    }
}
