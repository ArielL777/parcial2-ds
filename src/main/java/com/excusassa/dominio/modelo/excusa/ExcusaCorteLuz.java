package com.excusassa.dominio.modelo.excusa;

import com.excusassa.dominio.modelo.empleado.Empleado;
import com.excusassa.dominio.modelo.empleado.SupervisorArea;

public class ExcusaCorteLuz extends ExcusaModerada {

    public ExcusaCorteLuz(String motivo, String descripcion, Empleado empleado) {
        super(motivo, descripcion, empleado);
    }

    @Override
    public void notificar(SupervisorArea supervisor) {
        supervisor.getEmailSender().enviarEmail(
                "EDESUR@mailEdesur.com.ar",
                supervisor.getEmail(),
                "Verificación de corte de luz",
                "¿Hubo un corte de luz en la zona recientemente?"
        );
    }

    @Override
    public boolean procesablePorSupervisor() {
        return true;
    }
}
