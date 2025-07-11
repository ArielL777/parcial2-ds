package com.excusassa.dominio.servicios.notificacion;

import com.excusassa.dominio.modelo.prontuario.Prontuario;

public interface IObserver {
    void actualizar(Prontuario prontuario);
}
