package com.cun.biblioteca.servicio;

public class NotificadorEmail implements INotificador {

    @Override
    public void enviarNotificacion(String destinatario, String mensaje) {
        System.out.println("[EMAIL] Para: " + destinatario + " | Mensaje: " + mensaje);
    }

    @Override
    public String getNombreCanal() {
        return "Email";
    }
}
