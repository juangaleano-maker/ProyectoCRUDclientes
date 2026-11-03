package com.prueba.clientes.exception;

public class DocumentoDuplicadoException extends RuntimeException {
    public DocumentoDuplicadoException(String documento) {
        super("Ya existe un cliente con el documento: " + documento);
    }
}
