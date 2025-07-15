package com.excusassa.sistema_excusa.servicios.notificacion;

import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;

public interface IObserver {
    void actualizar(Prontuario prontuario);
}
