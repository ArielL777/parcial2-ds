package com.excusassa.sistema_excusa.dominio.modelo.empleado;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.servicios.encargado.EncargadoAbstracto;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;

public class SupervisorArea extends EncargadoAbstracto {

    public SupervisorArea(String nombre, String email, int nroLegajo,
                          ModoTrabajo modoTrabajo, EmailSender emailSender) {
        super(nombre, email, nroLegajo, modoTrabajo, emailSender);
    }

    @Override
    public boolean puedeProcesar(Excusa excusa) {
        return excusa.getTipo().esModerada();
    }

    @Override
    public void procesarExcusaInterna(Excusa excusa) {
        switch (excusa.getTipo()) {
            case CORTE_LUZ:
                emailSender.enviarEmail(
                        "EDESUR@mailfake.com.ar",
                        this.getEmail(),
                        "Consulta por corte de suministro",
                        "Se registró una demora por corte de luz para el empleado "
                                + excusa.getEmpleado().getNombre() + " (" + excusa.getTipo().getMotivo() + "). ¿Pueden confirmar el incidente?"
                );
                break;
            case CUIDADO_FAMILIAR:
                enviarEmailAprobacion(excusa, "Hemos recibido tu justificativo por cuidado de un familiar. Esperamos que esté todo bien.");
                break;
            default:
                enviarEmailAprobacion(excusa, "Tu excusa ha sido recibida por tu supervisor.");
                break;
        }
    }
}