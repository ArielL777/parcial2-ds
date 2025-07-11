package com.excusassa.infraestructura.excepciones;

public class OperacionNoSoportadaException extends RuntimeException {
    public OperacionNoSoportadaException(String mensaje) {
        super(mensaje);
    }
}
