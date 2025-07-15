package com.excusassa.sistema_excusa.dominio.modelo.empleado;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.servicios.encargado.EncargadoAbstracto;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;

public class Recepcionista extends EncargadoAbstracto {

    public Recepcionista(String nombre, String email, int nroLegajo,
                         ModoTrabajo modoTrabajo, EmailSender emailSender) {
        super(nombre, email, nroLegajo, modoTrabajo, emailSender);
    }

    @Override
    public boolean puedeProcesar(Excusa excusa) {
        return excusa.getTipo().esTrivial();
    }

    @Override
    public void procesarExcusaInterna(Excusa excusa) {
        excusa.setNombreEncargadoQueProceso(this.getNombre());
        enviarEmailAprobacion(excusa, "La licencia fue aceptada.");
    }
}
