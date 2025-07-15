package com.excusassa.sistema_excusa.servicios.notificacion;

import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificadorCEO implements IObservable {

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
        for (IObserver observer : observers) {
            observer.actualizar(prontuario);
        }
    }

    public static void resetInstance() {
        instancia = null;
    }
}