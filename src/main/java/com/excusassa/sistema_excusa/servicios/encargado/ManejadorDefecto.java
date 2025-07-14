package com.excusassa.sistema_excusa.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.infraestructura.excepciones.OperacionNoSoportadaException;

public class ManejadorDefecto implements IEncargado {

    @Override
    public void manejarExcusa(Excusa excusa) {
        System.out.println("Excusa rechazada: necesitamos pruebas contundentes.");
        System.out.println("Motivo: " + excusa.getTipo().getMotivo());
        System.out.println("Empleado: " + excusa.getEmpleado().getNombre());
    }

    @Override
    public void setSiguiente(IEncargado siguiente) {
        throw new OperacionNoSoportadaException("ManejadorDefecto no permite setear un siguiente.");
    }
}
