package com.cun.biblioteca.modelo;

public class Revista extends MaterialBiblioteca {

    private int numeroEdicion;

    public Revista(String codigo, String titulo, int anioPublicacion, int numeroEdicion) {
        super(codigo, titulo, anioPublicacion);
        this.numeroEdicion = numeroEdicion;
    }

    @Override
    public int getDiasPrestamoPermitidos() {
        return 3;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    @Override
    public String toString() {
        return "Revista{" +
                "codigo='" + codigo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", anio=" + anioPublicacion +
                ", numeroEdicion=" + numeroEdicion +
                ", disponible=" + disponible +
                '}';
    }
}
