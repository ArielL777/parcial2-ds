package com.excusassa.sistema_excusa.dominio.modelo.empleado;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.servicios.encargado.EncargadoAbstracto;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;

public class GerenteRRHH extends EncargadoAbstracto {

    public GerenteRRHH(String nombre, String email, int nroLegajo,
                       ModoTrabajo modoTrabajo, EmailSender emailSender) {
        super(nombre, email, nroLegajo, modoTrabajo, emailSender);
    }

    @Override
    public boolean puedeProcesar(Excusa excusa) {
        return excusa.getTipo().esCompleja();
    }

    @Override
    public void procesarExcusaInterna(Excusa excusa) {
        enviarEmailAprobacion(excusa, "La excusa fue aceptada por el Gerente de RRHH.");
    }
}