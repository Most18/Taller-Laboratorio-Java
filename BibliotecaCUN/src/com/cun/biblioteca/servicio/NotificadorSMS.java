package com.cun.biblioteca.servicio;

public class NotificadorSMS implements INotificador {

    @Override
    public void enviarNotificacion(String destinatario, String mensaje) {
        System.out.println("[SMS] Para: " + destinatario + " | Mensaje: " + mensaje);
    }

    @Override
    public String getNombreCanal() {
        return "SMS";
    }
}
