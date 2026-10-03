package com.cun.biblioteca.servicio;

import com.cun.biblioteca.modelo.DVD;
import com.cun.biblioteca.modelo.Libro;
import com.cun.biblioteca.modelo.MaterialBiblioteca;
import com.cun.biblioteca.modelo.Revista;
import com.cun.biblioteca.modelo.Tesis;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MaterialFactory {

    private static final Map<String, Function<String[], MaterialBiblioteca>> REGISTRO = new HashMap<>();

    static {
        REGISTRO.put("LIBRO", args -> new Libro(args[0], args[1], args[2], Integer.parseInt(args[3])));
        REGISTRO.put("REVISTA", args -> new Revista(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3])));
        REGISTRO.put("DVD", args -> new DVD(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3])));
        REGISTRO.put("TESIS", args -> new Tesis(args[0], args[1], Integer.parseInt(args[2]), args[3]));
    }

    public static MaterialBiblioteca crear(String tipo, String... args) {
        Function<String[], MaterialBiblioteca> creador = REGISTRO.get(tipo.toUpperCase());
        if (creador == null) {
            throw new IllegalArgumentException("Tipo de material no soportado: " + tipo);
        }
        return creador.apply(args);
    }
}
