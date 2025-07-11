package com.excusassa.dominio.servicios.modotrabajo;

import com.excusassa.dominio.servicios.encargado.EncargadoAbstracto;
import com.excusassa.dominio.modelo.excusa.IExcusa;

public class ModoVago implements IModoTrabajo {

    @Override
    public boolean esVago() {
        return true;
    }
    @Override
    public void accionAlDelegar(EncargadoAbstracto encargado, IExcusa excusa) {

    }
}


