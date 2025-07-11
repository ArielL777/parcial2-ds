package com.excusassa.dominio.servicios.modotrabajo;

import com.excusassa.dominio.servicios.encargado.EncargadoAbstracto;
import com.excusassa.dominio.modelo.excusa.IExcusa;

public class ModoProductivo implements IModoTrabajo {

    @Override
    public boolean esVago() {
        return false;
    }

    @Override
    public void accionAlDelegar(EncargadoAbstracto encargado, IExcusa excusa) {
        encargado.getEmailSender().enviarEmail(
                "cto@excusassa.com",
                encargado.getEmail(),
                "Excusa derivada",
                "Se derivó una excusa de tipo: " + excusa.getMotivo()
        );
    }
}

