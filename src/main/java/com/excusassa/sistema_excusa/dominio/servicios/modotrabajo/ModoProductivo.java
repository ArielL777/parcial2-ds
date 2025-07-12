package com.excusassa.sistema_excusa.dominio.servicios.modotrabajo;

import com.excusassa.sistema_excusa.dominio.servicios.encargado.EncargadoAbstracto;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;

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

