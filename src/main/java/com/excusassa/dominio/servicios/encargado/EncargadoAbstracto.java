package com.excusassa.dominio.servicios.encargado;

import com.excusassa.dominio.modelo.empleado.Empleado;
import com.excusassa.dominio.servicios.modotrabajo.IModoTrabajo;
import com.excusassa.dominio.servicios.modotrabajo.ModoNormal;
import com.excusassa.infraestructura.email.EmailSender;
import com.excusassa.dominio.modelo.excusa.IExcusa;

public abstract class EncargadoAbstracto extends Empleado implements IEncargado {

    protected IEncargado siguiente;
    protected IModoTrabajo modoTrabajo;
    protected EmailSender emailSender;

    public EncargadoAbstracto(String nombre,
                     String email,
                     int nroLegajo,
                     EmailSender emailSender) {
        super(nombre, email, nroLegajo);
        this.modoTrabajo  = new ModoNormal();
        this.emailSender  = emailSender;
    }

    public EncargadoAbstracto(String nombre,
                     String email,
                     int nroLegajo,
                     IModoTrabajo modoTrabajo,
                     EmailSender emailSender) {
        super(nombre, email, nroLegajo);
        this.modoTrabajo  = modoTrabajo;
        this.emailSender  = emailSender;
    }

    public EmailSender getEmailSender() {
        return emailSender;
    }

    @Override
    public void manejarExcusa(IExcusa excusa) {
        if (modoTrabajo.esVago()) {
            delegarASiguiente(excusa);
            return;
        }
        if (puedeProcesar(excusa)) {
            procesarExcusaInterna(excusa);
        } else {
            modoTrabajo.accionAlDelegar(this, excusa);
            delegarASiguiente(excusa);
        }
    }

    @Override
    public void setSiguiente(IEncargado siguiente) {
        this.siguiente = siguiente;
    }

    public void delegarASiguiente(IExcusa excusa) {
        siguiente.manejarExcusa(excusa);
    }

    protected void enviarEmailAprobacion(IExcusa excusa, String cuerpoMensaje) {
        this.emailSender.enviarEmail(
                excusa.getEmpleado().getEmail(),
                this.getEmail(),
                "motivo demora",
                cuerpoMensaje
        );
    }

    public abstract boolean puedeProcesar(IExcusa excusa);
    public abstract void procesarExcusaInterna(IExcusa excusa);

}
