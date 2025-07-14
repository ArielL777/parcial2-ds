package com.excusassa.sistema_excusa.dominio.modelo.empleado;

import com.excusassa.sistema_excusa.servicios.encargado.EncargadoAbstracto;
import com.excusassa.sistema_excusa.servicios.notificacion.NotificadorCEO;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.servicios.modotrabajo.IModoTrabajo;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.servicios.notificacion.IObserver;
import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import com.excusassa.sistema_excusa.servicios.prontuario.ProntuarioService;


public class CEO extends EncargadoAbstracto implements IObserver {

    private final ProntuarioService prontuarioService;


    public CEO(String nombre, String email, int nroLegajo,
               IModoTrabajo modoTrabajo, EmailSender emailSender, ProntuarioService prontuarioService) {
        super(nombre, email, nroLegajo, modoTrabajo, emailSender);
        this.prontuarioService = prontuarioService;
        NotificadorCEO.getInstance().agregarObserver(this);
    }

    @Override
    public boolean puedeProcesar(IExcusa excusa) {
        return excusa.procesablePorCEO();
    }

    @Override
    public void procesarExcusaInterna(IExcusa excusa) {
        prontuarioService.crearYPersistirProntuario(excusa.getEmpleado(), excusa);
        enviarEmailAprobacion(excusa, "La excusa fue aceptada por el Gerente de RRHH.");
    }

    @Override
    public void actualizar(Prontuario prontuario) {
        System.out.println("CEO " + this.getNombre() + " notificado de nuevo prontuario.");
    }
}
