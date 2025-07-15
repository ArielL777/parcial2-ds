package com.excusassa.sistema_excusa.infraestructura.excepciones;

public class OperacionNoSoportadaException extends RuntimeException {
    public OperacionNoSoportadaException(String mensaje) {
        super(mensaje);
    }
}
