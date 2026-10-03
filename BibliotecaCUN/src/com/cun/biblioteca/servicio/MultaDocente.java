package com.cun.biblioteca.servicio;

public class MultaDocente implements IEstrategiaMulta {

    @Override
    public double calcular(int diasRetraso) {
        return diasRetraso * 1500.0;
    }

    @Override
    public String getNombre() {
        return "Multa docente";
    }
}
