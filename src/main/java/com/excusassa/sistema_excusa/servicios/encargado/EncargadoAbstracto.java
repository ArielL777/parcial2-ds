package com.excusassa.sistema_excusa.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import lombok.Getter;

public abstract class EncargadoAbstracto extends Empleado implements IEncargado {

    protected IEncargado siguiente;
    protected ModoTrabajo modoTrabajo;
    @Getter
    protected EmailSender emailSender;

    public EncargadoAbstracto(String nombre, String email, int nroLegajo,
                              ModoTrabajo modoTrabajo, EmailSender emailSender) {
        super(nombre, email, nroLegajo);
        this.modoTrabajo = modoTrabajo;
        this.emailSender = emailSender;
    }

    @Override
    public void manejarExcusa(Excusa excusa) {
        modoTrabajo.procesar(this, excusa);
    }

    @Override
    public void setSiguiente(IEncargado siguiente) {
        this.siguiente = siguiente;
    }

    public void delegarASiguiente(Excusa excusa) {
        siguiente.manejarExcusa(excusa);
    }

    protected void enviarEmailAprobacion(Excusa excusa, String cuerpoMensaje) {
        this.emailSender.enviarEmail(
                excusa.getEmpleado().getEmail(),
                this.getEmail(),
                "motivo demora",
                cuerpoMensaje
        );
    }

    public abstract boolean puedeProcesar(Excusa excusa);
    public abstract void procesarExcusaInterna(Excusa excusa);
}
