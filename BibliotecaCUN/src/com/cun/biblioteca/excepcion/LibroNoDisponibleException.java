package com.cun.biblioteca.excepcion;

public class LibroNoDisponibleException extends Exception {

    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
