package com.excusassa.dominio.modelo.empleado;

import com.excusassa.dominio.servicios.encargado.EncargadoAbstracto;
import com.excusassa.infraestructura.persistencia.AdministradorProntuarios;
import com.excusassa.dominio.servicios.notificacion.NotificadorCEO;
import com.excusassa.infraestructura.email.EmailSender;
import com.excusassa.dominio.servicios.modotrabajo.IModoTrabajo;
import com.excusassa.dominio.modelo.excusa.IExcusa;
import com.excusassa.dominio.servicios.notificacion.IObserver;
import com.excusassa.dominio.modelo.prontuario.Prontuario;


public class CEO extends EncargadoAbstracto implements IObserver {

    public CEO(String nombre, String email, int nroLegajo,
               IModoTrabajo modoTrabajo, EmailSender emailSender) {
        super(nombre, email, nroLegajo, modoTrabajo, emailSender);

        NotificadorCEO.getInstance().agregarObserver(this);
    }

    @Override
    public boolean puedeProcesar(IExcusa excusa) {
        return excusa.procesablePorCEO();
    }

    @Override
    public void procesarExcusaInterna(IExcusa excusa) {
        AdministradorProntuarios.getInstance().crearYPersistirProntuario(
                excusa.getEmpleado(),
                excusa
        );
        enviarEmailAprobacion(excusa, "La excusa fue aceptada por el Gerente de RRHH.");
    }

    @Override
    public void actualizar(Prontuario prontuario) {
        System.out.println("CEO " + this.getNombre() + " notificado de nuevo prontuario.");
    }
}
