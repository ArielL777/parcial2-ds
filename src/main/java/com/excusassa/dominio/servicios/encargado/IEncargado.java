package com.excusassa.dominio.servicios.encargado;

import com.excusassa.dominio.modelo.excusa.IExcusa;

public interface IEncargado {
    void manejarExcusa(IExcusa excusa);
    void setSiguiente(IEncargado siguiente);
}
