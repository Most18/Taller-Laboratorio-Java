package com.cun.biblioteca.modelo;

public class Tesis extends MaterialBiblioteca {

    private String director;

    public Tesis(String codigo, String titulo, int anioPublicacion, String director) {
        super(codigo, titulo, anioPublicacion);
        this.director = director;
    }

    @Override
    public int getDiasPrestamoPermitidos() {
        return 0;
    }

    public String getDirector() {
        return director;
    }

    @Override
    public String toString() {
        return "Tesis{" +
                "codigo='" + codigo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", anio=" + anioPublicacion +
                ", director='" + director + '\'' +
                ", disponible=" + disponible +
                ", diasPrestamo=" + getDiasPrestamoPermitidos() +
                '}';
    }
}
