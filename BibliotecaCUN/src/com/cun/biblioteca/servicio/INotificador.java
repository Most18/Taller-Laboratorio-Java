package com.cun.biblioteca.servicio;

public interface INotificador {

    void enviarNotificacion(String destinatario, String mensaje);

    String getNombreCanal();
}
