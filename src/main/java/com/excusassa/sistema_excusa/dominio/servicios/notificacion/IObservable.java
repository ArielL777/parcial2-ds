package com.excusassa.sistema_excusa.dominio.servicios.notificacion;

import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;

public interface IObservable {
    void agregarObserver(IObserver observer);
    void removerObserver(IObserver observer);
    void notificarObservers(Prontuario prontuario);
}
