package com.excusassa.sistema_excusa.dominio.servicios.notificacion;

import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;

import java.util.ArrayList;
import java.util.List;

// FÍJATE AQUÍ: Esta es la clase que AHORA implementa la interfaz
public class NotificadorCEO implements IObservable {

    // También es un Singleton para tener un único punto de notificación
    private static NotificadorCEO instancia;
    private final List<IObserver> observers;

    private NotificadorCEO() {
        observers = new ArrayList<>();
    }

    public static NotificadorCEO getInstance() {
        if (instancia == null) {
            instancia = new NotificadorCEO();
        }
        return instancia;
    }

    // A partir de aquí, son los métodos que cortamos y pegamos
    // de la versión vieja de AdministradorProntuarios.

    @Override
    public void agregarObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removerObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notificarObservers(Prontuario prontuario) {
        // Su única lógica es recorrer la lista de observers y notificarles.
        for (IObserver observer : observers) {
            observer.actualizar(prontuario);
        }
    }

    public static void resetInstance() {
        instancia = null;
    }
}