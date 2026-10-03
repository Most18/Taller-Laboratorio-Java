package com.cun.biblioteca.servicio;

public class ServicioNotificaciones {

    private INotificador notificador;

    public ServicioNotificaciones(INotificador notificador) {
        this.notificador = notificador;
    }

    public void setNotificador(INotificador notificador) {
        this.notificador = notificador;
    }

    public void notificarVencimiento(String usuario, String tituloLibro) {
        String mensaje = "Su préstamo del libro '" + tituloLibro
                + "' ha vencido. Por favor devuélvalo.";
        notificador.enviarNotificacion(usuario, mensaje);
    }

    public String getCanalActual() {
        return notificador.getNombreCanal();
    }
}
