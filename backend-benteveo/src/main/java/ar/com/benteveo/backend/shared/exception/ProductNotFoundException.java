package ar.com.benteveo.backend.shared.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Producto no encontrado");
    }
}
