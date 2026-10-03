package com.cun.biblioteca.servicio;

import com.cun.biblioteca.modelo.Prestamo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculadoraMultas {

    private static final double MULTA_POR_DIA = 2000.0;

    public double calcularMulta(Prestamo prestamo) {
        if (prestamo.isDevuelto()) {
            return 0.0;
        }
        LocalDate hoy = LocalDate.now();
        if (hoy.isAfter(prestamo.getFechaDevolucionEsperada())) {
            long diasRetraso = ChronoUnit.DAYS.between(
                    prestamo.getFechaDevolucionEsperada(), hoy);
            return diasRetraso * MULTA_POR_DIA;
        }
        return 0.0;
    }
}
