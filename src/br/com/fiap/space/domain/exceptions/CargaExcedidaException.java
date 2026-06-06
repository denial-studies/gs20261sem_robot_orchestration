package br.com.fiap.space.domain.exceptions;

public class CargaExcedidaException extends RuntimeException {
    public CargaExcedidaException(String message) {
        super(message);
    }
}
