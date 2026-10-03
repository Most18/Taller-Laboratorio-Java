package com.cun.biblioteca.servicio;

public class ConfiguracionBiblioteca {

    private static ConfiguracionBiblioteca instancia;

    private String nombreBiblioteca = "Biblioteca CUN Ibagué";
    private double multaPorDia = 2000.0;
    private int maxDiasPrestamo = 15;

    private ConfiguracionBiblioteca() {
    }

    public static synchronized ConfiguracionBiblioteca getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionBiblioteca();
        }
        return instancia;
    }

    public String getNombreBiblioteca() {
        return nombreBiblioteca;
    }

    public double getMultaPorDia() {
        return multaPorDia;
    }

    public void setMultaPorDia(double multaPorDia) {
        this.multaPorDia = multaPorDia;
    }

    public int getMaxDiasPrestamo() {
        return maxDiasPrestamo;
    }
}
