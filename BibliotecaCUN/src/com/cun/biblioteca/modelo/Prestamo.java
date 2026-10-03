package com.cun.biblioteca.modelo;

import com.cun.biblioteca.excepcion.LibroNoDisponibleException;

import java.time.LocalDate;

public class Prestamo {

    private Libro libro;
    private String nombreUsuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private boolean devuelto;

    public Prestamo(Libro libro, String nombreUsuario, int diasPrestamo) throws LibroNoDisponibleException {
        if (!libro.isDisponible()) {
            throw new LibroNoDisponibleException(
                    "El libro '" + libro.getTitulo() + "' no está disponible.");
        }
        this.libro = libro;
        libro.setDisponible(false);
        this.nombreUsuario = nombreUsuario;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEsperada = fechaPrestamo.plusDays(diasPrestamo);
        this.devuelto = false;
    }

    public Libro getLibro() {
        return libro;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void marcarComoDevuelto() {
        this.devuelto = true;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "libro=" + libro.getTitulo() +
                ", usuario='" + nombreUsuario + '\'' +
                ", devolucionEsperada=" + fechaDevolucionEsperada +
                ", devuelto=" + devuelto +
                '}';
    }
}
