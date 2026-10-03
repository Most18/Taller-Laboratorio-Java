package com.cun.biblioteca.modelo;

import com.cun.biblioteca.excepcion.DatosInvalidosException;

public abstract class MaterialBiblioteca {

    protected String codigo;
    protected String titulo;
    protected int anioPublicacion;
    protected boolean disponible = true;

    public MaterialBiblioteca(String codigo, String titulo, int anioPublicacion) {
        if (codigo == null || codigo.isBlank()) {
            throw new DatosInvalidosException("El código no puede estar vacío.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new DatosInvalidosException("El título no puede estar vacío.");
        }
        if (anioPublicacion < 0 || anioPublicacion > java.time.Year.now().getValue()) {
            throw new DatosInvalidosException("Año de publicación inválido: " + anioPublicacion);
        }
        this.codigo = codigo;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
    }

    public void marcarComoPrestado() {
        this.disponible = false;
    }

    public void marcarComoDevuelto() {
        this.disponible = true;
    }

    public abstract int getDiasPrestamoPermitidos();

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "codigo='" + codigo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", anio=" + anioPublicacion +
                ", disponible=" + disponible +
                '}';
    }
}
