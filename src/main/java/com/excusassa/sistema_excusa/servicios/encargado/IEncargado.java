package com.excusassa.sistema_excusa.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;

public interface IEncargado {
    void manejarExcusa(Excusa excusa);
    void setSiguiente(IEncargado siguiente);
    IEncargado getSiguiente();
}