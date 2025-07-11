package com.excusassa.dominio.modelo.excusa;

import com.excusassa.dominio.modelo.empleado.Empleado;
import com.excusassa.dominio.modelo.empleado.SupervisorArea;

public class ExcusaCuidadoFamiliar extends ExcusaModerada {

    public ExcusaCuidadoFamiliar(String motivo, String descripcion, Empleado empleado) {
        super(motivo, descripcion, empleado);
    }

    @Override
    public void notificar(SupervisorArea supervisor) {
        supervisor.getEmailSender().enviarEmail(
                this.getEmpleado().getEmail(),
                supervisor.getEmail(),
                "consulta salud",
                "¿Está todo bien con el familiar a cargo?"
        );
    }

    @Override
    public boolean procesablePorSupervisor() {
        return true;
    }
}
