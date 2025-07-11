package com.excusassa.dominio.servicios.encargado;

import com.excusassa.dominio.servicios.modotrabajo.IModoTrabajo;
import com.excusassa.infraestructura.email.EmailSender;
import com.excusassa.dominio.modelo.excusa.IExcusa;
import com.excusassa.dominio.modelo.empleado.CEO;
import com.excusassa.dominio.modelo.empleado.GerenteRRHH;
import com.excusassa.dominio.modelo.empleado.Recepcionista;
import com.excusassa.dominio.modelo.empleado.SupervisorArea;

public class CadenaEncargados {

   final private IEncargado primerEncargado;

    public CadenaEncargados(IModoTrabajo modoTrabajo, EmailSender emailSender) {
        this.primerEncargado = construirCadena(modoTrabajo, emailSender);
    }

    private IEncargado construirCadena(IModoTrabajo modoTrabajo, EmailSender emailSender) {
        IEncargado recepcionista = new Recepcionista("Ana", "ana@excusassa.com", 1001, modoTrabajo, emailSender);
        IEncargado supervisor = new SupervisorArea("Luis", "luis@excusassa.com", 1002, modoTrabajo, emailSender);
        IEncargado gerente = new GerenteRRHH("Carlos", "carlos@excusassa.com", 1003, modoTrabajo, emailSender);
        IEncargado ceo = new CEO("Lucía", "lucia@excusassa.com", 1004, modoTrabajo, emailSender);
        IEncargado defecto = new ManejadorDefecto();

        recepcionista.setSiguiente(supervisor);
        supervisor.setSiguiente(gerente);
        gerente.setSiguiente(ceo);
        ceo.setSiguiente(defecto);

        return recepcionista;
    }

    public void procesarExcusa(IExcusa excusa) {
        primerEncargado.manejarExcusa(excusa);
    }
}
