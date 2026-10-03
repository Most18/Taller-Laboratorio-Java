package com.cun.biblioteca.servicio;

import com.cun.biblioteca.modelo.MaterialBiblioteca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CatalogoBiblioteca {

    private List<MaterialBiblioteca> materiales = new ArrayList<>();
    private Map<String, MaterialBiblioteca> porCodigo = new HashMap<>();
    private Set<String> categorias = new HashSet<>();
    private Queue<String> colaReservas = new LinkedList<>();

    public void agregarMaterial(MaterialBiblioteca material) {
        materiales.add(material);
        porCodigo.put(material.getCodigo(), material);
        categorias.add(material.getClass().getSimpleName().toUpperCase());
    }

    public MaterialBiblioteca buscarPorCodigo(String codigo) {
        return porCodigo.get(codigo);
    }

    public List<MaterialBiblioteca> listarTodos() {
        return new ArrayList<>(materiales);
    }

    public Set<String> getCategorias() {
        return new HashSet<>(categorias);
    }

    public void agregarReserva(String usuario) {
        colaReservas.offer(usuario);
    }

    public String atenderSiguienteReserva() {
        return colaReservas.poll();
    }

    public int getTotalReservas() {
        return colaReservas.size();
    }
}
