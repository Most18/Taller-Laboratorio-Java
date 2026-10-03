package com.cun.biblioteca.servicio;

public class MultaEstandar implements IEstrategiaMulta {

    @Override
    public double calcular(int diasRetraso) {
        return diasRetraso * 2000.0;
    }

    @Override
    public String getNombre() {
        return "Multa estándar";
    }
}
