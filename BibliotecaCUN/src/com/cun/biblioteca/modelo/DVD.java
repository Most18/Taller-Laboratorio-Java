package com.cun.biblioteca.modelo;

public class DVD extends MaterialBiblioteca {

    private int duracionMinutos;

    public DVD(String codigo, String titulo, int anioPublicacion, int duracionMinutos) {
        super(codigo, titulo, anioPublicacion);
        this.duracionMinutos = duracionMinutos;
    }

    @Override
    public int getDiasPrestamoPermitidos() {
        return 5;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    @Override
    public String toString() {
        return "DVD{" +
                "codigo='" + codigo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", anio=" + anioPublicacion +
                ", duracionMinutos=" + duracionMinutos +
                ", disponible=" + disponible +
                '}';
    }
}
