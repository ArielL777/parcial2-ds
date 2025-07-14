package com.excusassa.sistema_excusa.dominio.modelo.empleado;

import com.excusassa.sistema_excusa.servicios.encargado.EncargadoAbstracto;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.servicios.modotrabajo.IModoTrabajo;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;

public class Recepcionista extends EncargadoAbstracto {

    public Recepcionista(String nombre, String email, int nroLegajo,
                         IModoTrabajo modoTrabajo, EmailSender emailSender) {
        super(nombre, email, nroLegajo, modoTrabajo, emailSender);
    }

    @Override
    public boolean puedeProcesar(IExcusa excusa) {
        return excusa.procesablePorRecepcionista();
    }

    @Override
    public void procesarExcusaInterna(IExcusa excusa) {
        enviarEmailAprobacion(excusa, "La licencia fue aceptada.");
    }
}
