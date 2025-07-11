package com.excusassa.dominio.servicios.notificacion;

import com.excusassa.dominio.modelo.prontuario.Prontuario;

public interface IObservable {
    void agregarObserver(IObserver observer);
    void removerObserver(IObserver observer);
    void notificarObservers(Prontuario prontuario);
}
