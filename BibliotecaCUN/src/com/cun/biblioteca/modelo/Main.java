package com.cun.biblioteca.modelo;

import com.cun.biblioteca.excepcion.DatosInvalidosException;
import com.cun.biblioteca.excepcion.LibroNoDisponibleException;
import com.cun.biblioteca.servicio.CatalogoBiblioteca;
import com.cun.biblioteca.servicio.ConfiguracionBiblioteca;
import com.cun.biblioteca.servicio.IEstrategiaMulta;
import com.cun.biblioteca.servicio.MaterialFactory;
import com.cun.biblioteca.servicio.MultaEstudiante;
import com.cun.biblioteca.servicio.NotificadorEmail;
import com.cun.biblioteca.servicio.NotificadorSMS;
import com.cun.biblioteca.servicio.NotificadorWhatsApp;
import com.cun.biblioteca.servicio.ServicioNotificaciones;

public class Main {

    public static void main(String[] args) {
        demostrarMaterialesYNotificaciones();
        demostrarExcepcionUnchecked();
        demostrarCatalogo();
        demostrarPatrones();
    }

    private static void demostrarMaterialesYNotificaciones() {
        MaterialBiblioteca[] materiales = new MaterialBiblioteca[] {
                new Libro("978-3-16-148410-0", "Cien Años de Soledad", "García Márquez", 1967),
                new Revista("R-001", "National Geographic", 2024, 215),
                new DVD("D-001", "El Padrino", 1972, 175),
                new Tesis("T-001", "Machine Learning en Agricultura", 2023, "Dr. Pérez")
        };

        System.out.println("=== Materiales de la biblioteca ===");
        for (MaterialBiblioteca m : materiales) {
            System.out.println(m + " | Días de préstamo: " + m.getDiasPrestamoPermitidos());
        }

        System.out.println("\n=== Notificaciones ===");
        ServicioNotificaciones servicio = new ServicioNotificaciones(new NotificadorEmail());
        System.out.println("Canal actual: " + servicio.getCanalActual());
        servicio.notificarVencimiento("Ana Gómez", "Cien Años de Soledad");

        servicio.setNotificador(new NotificadorWhatsApp());
        System.out.println("Canal actual: " + servicio.getCanalActual());
        servicio.notificarVencimiento("Carlos Ruiz", "El Principito");

        servicio.setNotificador(new NotificadorSMS());
        System.out.println("Canal actual: " + servicio.getCanalActual());
        servicio.notificarVencimiento("Luisa Fernández", "1984");
    }

    private static void demostrarExcepcionUnchecked() {
        System.out.println("\n=== Excepción unchecked ===");
        try {
            Libro libroInvalido = new Libro("", "Sin ISBN", "Autor X", 2024);
            System.out.println(libroInvalido);
        } catch (DatosInvalidosException e) {
            System.err.println("ERROR DE DATOS: " + e.getMessage());
        }
        System.out.println("El programa continúa ejecutándose sin colapsar.");
    }

    private static void demostrarCatalogo() {
        CatalogoBiblioteca catalogo = new CatalogoBiblioteca();
        catalogo.agregarMaterial(new Libro("L-001", "Cien Años de Soledad", "García Márquez", 1967));
        catalogo.agregarMaterial(new Revista("R-001", "National Geographic", 2024, 215));
        catalogo.agregarMaterial(new DVD("D-001", "El Padrino", 1972, 175));
        catalogo.agregarMaterial(new Tesis("T-001", "ML en Agricultura", 2023, "Dr. Pérez"));

        System.out.println("\n=== Catálogo completo ===");
        catalogo.listarTodos().forEach(System.out::println);

        System.out.println("\n=== Búsqueda por código ===");
        System.out.println(catalogo.buscarPorCodigo("L-001"));

        System.out.println("\n=== Categorías únicas ===");
        System.out.println(catalogo.getCategorias());

        System.out.println("\n=== Cola de reservas ===");
        catalogo.agregarReserva("Ana");
        catalogo.agregarReserva("Carlos");
        catalogo.agregarReserva("Luisa");
        System.out.println("Atendiendo a: " + catalogo.atenderSiguienteReserva());
        System.out.println("Reservas pendientes: " + catalogo.getTotalReservas());
    }

    private static void demostrarPatrones() {
        System.out.println("\n=== Singleton ===");
        ConfiguracionBiblioteca config = ConfiguracionBiblioteca.getInstancia();
        System.out.println("Biblioteca: " + config.getNombreBiblioteca());
        System.out.println("Multa por día: " + config.getMultaPorDia());

        System.out.println("\n=== Factory ===");
        MaterialBiblioteca libro = MaterialFactory.crear("LIBRO", "L-002", "1984", "George Orwell", "1949");
        MaterialBiblioteca revista = MaterialFactory.crear("REVISTA", "R-002", "Muy Interesante", "2024", "120");
        MaterialBiblioteca quijote = MaterialFactory.crear("LIBRO", "L-003", "El Quijote", "Cervantes", "1605");
        System.out.println("Creado por factory: " + libro);
        System.out.println("Creado por factory: " + revista);
        System.out.println("Creado por factory: " + quijote);

        System.out.println("\n=== Strategy ===");
        IEstrategiaMulta estrategia = new MultaEstudiante();
        System.out.println("Estrategia: " + estrategia.getNombre());
        System.out.println("Multa por 5 días: $" + estrategia.calcular(5));

        System.out.println("\n=== Préstamo con excepción checked ===");
        try {
            Libro libro1 = new Libro("978-0-14-017739-8", "El Principito", "Antoine de Saint-Exupéry", 1943);
            Prestamo p1 = new Prestamo(libro1, "Ana Gómez", 15);
            System.out.println("Préstamo exitoso: " + p1);
            Prestamo p2 = new Prestamo(libro1, "Carlos Ruiz", 7);
            System.out.println("Préstamo exitoso: " + p2);
        } catch (LibroNoDisponibleException e) {
            System.err.println("ERROR DE NEGOCIO: " + e.getMessage());
        }
    }
}
