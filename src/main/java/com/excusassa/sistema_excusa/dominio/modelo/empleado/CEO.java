package com.excusassa.sistema_excusa.dominio.modelo.empleado;

import com.excusassa.sistema_excusa.dominio.servicios.encargado.EncargadoAbstracto;
import com.excusassa.sistema_excusa.infraestructura.persistencia.AdministradorProntuarios;
import com.excusassa.sistema_excusa.dominio.servicios.notificacion.NotificadorCEO;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.dominio.servicios.modotrabajo.IModoTrabajo;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.dominio.servicios.notificacion.IObserver;
import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;


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
