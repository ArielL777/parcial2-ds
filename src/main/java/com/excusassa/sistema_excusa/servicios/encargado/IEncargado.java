package com.excusassa.sistema_excusa.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;

public interface IEncargado {
    void manejarExcusa(IExcusa excusa);
    void setSiguiente(IEncargado siguiente);
}
