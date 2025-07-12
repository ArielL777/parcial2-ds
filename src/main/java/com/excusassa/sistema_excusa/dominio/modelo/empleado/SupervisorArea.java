package com.excusassa.sistema_excusa.dominio.modelo.empleado;

import com.excusassa.sistema_excusa.dominio.servicios.encargado.EncargadoAbstracto;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.dominio.servicios.modotrabajo.IModoTrabajo;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;

public class SupervisorArea extends EncargadoAbstracto {

    public SupervisorArea(String nombre, String email, int nroLegajo,
                          IModoTrabajo modoTrabajo, EmailSender emailSender) {
        super(nombre, email, nroLegajo, modoTrabajo, emailSender);
    }

    @Override
    public boolean puedeProcesar(IExcusa excusa) {
        return excusa.procesablePorSupervisor();
    }

    @Override
    public void procesarExcusaInterna(IExcusa excusa) {
        excusa.notificar(this);
    }
}
