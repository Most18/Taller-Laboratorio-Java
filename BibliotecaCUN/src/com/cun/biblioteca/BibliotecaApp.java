package com.cun.biblioteca;

import com.cun.biblioteca.excepcion.LibroNoDisponibleException;
import com.cun.biblioteca.modelo.Libro;
import com.cun.biblioteca.modelo.MaterialBiblioteca;
import com.cun.biblioteca.modelo.Prestamo;
import com.cun.biblioteca.servicio.CalculadoraMultas;
import com.cun.biblioteca.servicio.CatalogoBiblioteca;
import com.cun.biblioteca.servicio.ConfiguracionBiblioteca;
import com.cun.biblioteca.servicio.IEstrategiaMulta;
import com.cun.biblioteca.servicio.MaterialFactory;
import com.cun.biblioteca.servicio.MultaDocente;
import com.cun.biblioteca.servicio.MultaEstandar;
import com.cun.biblioteca.servicio.MultaEstudiante;
import com.cun.biblioteca.servicio.NotificadorEmail;
import com.cun.biblioteca.servicio.NotificadorSMS;
import com.cun.biblioteca.servicio.NotificadorWhatsApp;
import com.cun.biblioteca.servicio.ServicioNotificaciones;

public class BibliotecaApp {

    public static void main(String[] args) {
        System.out.println("========== BIBLIOTECA CUN ==========");

        System.out.println("\n1. Configuración (Singleton)");
        ConfiguracionBiblioteca config = ConfiguracionBiblioteca.getInstancia();
        System.out.println("Biblioteca: " + config.getNombreBiblioteca());
        System.out.println("Multa por día en configuración: $" + config.getMultaPorDia());
        System.out.println("Máximo de días de préstamo: " + config.getMaxDiasPrestamo());
        ConfiguracionBiblioteca otra = ConfiguracionBiblioteca.getInstancia();
        System.out.println("¿Es la misma instancia? " + (config == otra));

        System.out.println("\n2. Crear materiales (Factory)");
        MaterialBiblioteca cienAnios = MaterialFactory.crear(
                "LIBRO", "L-001", "Cien Años de Soledad", "Gabriel García Márquez", "1967");
        MaterialBiblioteca revista = MaterialFactory.crear(
                "REVISTA", "R-001", "National Geographic", "2024", "215");
        MaterialBiblioteca dvd = MaterialFactory.crear(
                "DVD", "D-001", "El Padrino", "1972", "175");
        MaterialBiblioteca tesis = MaterialFactory.crear(
                "TESIS", "T-001", "Machine Learning en Agricultura", "2023", "Dr. Pérez");

        System.out.println("\n3. Agregar al catálogo (Collections)");
        CatalogoBiblioteca catalogo = new CatalogoBiblioteca();
        catalogo.agregarMaterial(cienAnios);
        catalogo.agregarMaterial(revista);
        catalogo.agregarMaterial(dvd);
        catalogo.agregarMaterial(tesis);

        System.out.println("\n4. Mostrar el catálogo");
        catalogo.listarTodos().forEach(material ->
                System.out.println(material + " | Días permitidos: " + material.getDiasPrestamoPermitidos()));
        System.out.println("Categorías: " + catalogo.getCategorias());
        System.out.println("Búsqueda L-001: " + catalogo.buscarPorCodigo("L-001"));

        System.out.println("\n5. Prestar un libro");
        Libro libroParaPrestar = (Libro) cienAnios;
        Prestamo prestamo = null;
        try {
            prestamo = new Prestamo(libroParaPrestar, "Ana Gómez", libroParaPrestar.getDiasPrestamoPermitidos());
            System.out.println("Préstamo exitoso: " + prestamo);
            System.out.println("¿El libro sigue disponible? " + libroParaPrestar.isDisponible());
        } catch (LibroNoDisponibleException e) {
            System.err.println("ERROR DE NEGOCIO: " + e.getMessage());
        }

        System.out.println("\n6 y 7. Intentar prestar el mismo libro y capturar la excepción");
        try {
            Prestamo segundo = new Prestamo(libroParaPrestar, "Carlos Ruiz", 7);
            System.out.println("Esto no debería imprimirse: " + segundo);
        } catch (LibroNoDisponibleException e) {
            System.err.println("ERROR DE NEGOCIO: " + e.getMessage());
        }

        System.out.println("\nNota sobre la tesis");
        System.out.println(tesis.getTitulo() + " permite " + tesis.getDiasPrestamoPermitidos()
                + " días. El taller no agrega una validación que impida prestarla.");
        System.out.println("Además, Prestamo solo recibe Libro, así que Revista, DVD y Tesis no pasan por ese constructor.");

        System.out.println("\n8. Notificar por distintos canales (interfaz)");
        ServicioNotificaciones servicio = new ServicioNotificaciones(new NotificadorEmail());
        System.out.println("Canal: " + servicio.getCanalActual());
        servicio.notificarVencimiento("Ana Gómez", libroParaPrestar.getTitulo());
        servicio.setNotificador(new NotificadorWhatsApp());
        System.out.println("Canal: " + servicio.getCanalActual());
        servicio.notificarVencimiento("Carlos Ruiz", "El Principito");
        servicio.setNotificador(new NotificadorSMS());
        System.out.println("Canal: " + servicio.getCanalActual());
        servicio.notificarVencimiento("Luisa Fernández", "1984");

        System.out.println("\n9 y 10. Cambiar estrategia de multa y mostrar resultados");
        int diasRetraso = 5;
        IEstrategiaMulta estrategia = new MultaEstandar();
        System.out.println(estrategia.getNombre() + " por " + diasRetraso + " días: $" + estrategia.calcular(diasRetraso));
        estrategia = new MultaEstudiante();
        System.out.println(estrategia.getNombre() + " por " + diasRetraso + " días: $" + estrategia.calcular(diasRetraso));
        estrategia = new MultaDocente();
        System.out.println(estrategia.getNombre() + " por " + diasRetraso + " días: $" + estrategia.calcular(diasRetraso));

        CalculadoraMultas calculadora = new CalculadoraMultas();
        if (prestamo != null) {
            System.out.println("CalculadoraMultas sobre el préstamo de hoy: $"
                    + calculadora.calcularMulta(prestamo));
            System.out.println("Sale 0 porque la fecha esperada todavía no pasó. Esta calculadora no usa las estrategias.");
        }

        System.out.println("\nCola de reservas");
        catalogo.agregarReserva("Ana");
        catalogo.agregarReserva("Carlos");
        catalogo.agregarReserva("Luisa");
        System.out.println("Atendiendo a: " + catalogo.atenderSiguienteReserva());
        System.out.println("Reservas pendientes: " + catalogo.getTotalReservas());

        System.out.println("\nDevolución, sin ocultar el hueco del taller");
        if (prestamo != null) {
            prestamo.marcarComoDevuelto();
            System.out.println("Prestamo.devuelto = " + prestamo.isDevuelto());
            System.out.println("Libro.disponible después de marcar el préstamo = " + libroParaPrestar.isDisponible());
            System.out.println("El libro sigue no disponible porque Prestamo.marcarComoDevuelto() no llama a setDisponible(true).");
            libroParaPrestar.marcarComoDevuelto();
            System.out.println("Después de llamar también a libro.marcarComoDevuelto(): " + libroParaPrestar.isDisponible());
        }

        System.out.println("\n11. Estado final de la biblioteca");
        System.out.println("Nombre: " + config.getNombreBiblioteca());
        System.out.println("Materiales registrados: " + catalogo.listarTodos().size());
        catalogo.listarTodos().forEach(System.out::println);
        System.out.println("Reservas pendientes: " + catalogo.getTotalReservas());
        System.out.println("========== FIN ==========");
    }
}
