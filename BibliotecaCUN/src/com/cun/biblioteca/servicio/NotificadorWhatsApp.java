package com.cun.biblioteca.servicio;

public class NotificadorWhatsApp implements INotificador {

    @Override
    public void enviarNotificacion(String destinatario, String mensaje) {
        System.out.println("[WHATSAPP] Para: " + destinatario + " | Mensaje: " + mensaje);
    }

    @Override
    public String getNombreCanal() {
        return "WhatsApp";
    }
}
