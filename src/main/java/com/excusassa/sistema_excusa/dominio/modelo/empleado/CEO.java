package com.excusassa.sistema_excusa.dominio.modelo.empleado;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.servicios.encargado.EncargadoAbstracto;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;
import com.excusassa.sistema_excusa.servicios.notificacion.IObserver;
import com.excusassa.sistema_excusa.servicios.prontuario.ProntuarioService;

public class CEO extends EncargadoAbstracto implements IObserver {

    private final ProntuarioService prontuarioService;

    public CEO(String nombre, String email, int nroLegajo,
               ModoTrabajo modoTrabajo, EmailSender emailSender, ProntuarioService prontuarioService) {
        super(nombre, email, nroLegajo, modoTrabajo, emailSender);
        this.prontuarioService = prontuarioService;
    }

    @Override
    public boolean puedeProcesar(Excusa excusa) {
        return excusa.getTipo().esInverosimil();
    }

    @Override
    public void procesarExcusaInterna(Excusa excusa) {
        excusa.setNombreEncargadoQueProceso(this.getNombre());
        prontuarioService.crearYPersistirProntuario(excusa.getEmpleado(), excusa);
        enviarEmailAprobacion(excusa, "Aprobado por creatividad.");
    }

    @Override
    public void actualizar(Prontuario prontuario) {
        System.out.println("CEO " + this.getNombre() + " notificado de nuevo prontuario: " + prontuario.getMotivoExcusa());
    }
}