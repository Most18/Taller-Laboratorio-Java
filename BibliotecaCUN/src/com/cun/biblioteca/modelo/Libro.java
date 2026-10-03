package com.cun.biblioteca.modelo;

import com.cun.biblioteca.excepcion.DatosInvalidosException;

public class Libro extends MaterialBiblioteca {

    private String isbn;
    private String autor;

    public Libro(String isbn, String titulo, String autor, int anioPublicacion) {
        super(isbn, titulo, anioPublicacion);
        if (autor == null || autor.isBlank()) {
            throw new DatosInvalidosException("El autor no puede estar vacío.");
        }
        this.isbn = isbn;
        this.autor = autor;
    }

    @Override
    public int getDiasPrestamoPermitidos() {
        return 15;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", anio=" + anioPublicacion +
                ", disponible=" + disponible +
                '}';
    }
}
