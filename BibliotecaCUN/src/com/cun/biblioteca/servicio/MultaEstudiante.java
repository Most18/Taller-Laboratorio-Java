package com.cun.biblioteca.servicio;

public class MultaEstudiante implements IEstrategiaMulta {

    @Override
    public double calcular(int diasRetraso) {
        return diasRetraso * 1000.0;
    }

    @Override
    public String getNombre() {
        return "Multa estudiante";
    }
}
