package com.excusassa.sistema_excusa.servicios.modotrabajo;

import com.excusassa.sistema_excusa.servicios.encargado.EncargadoAbstracto;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;

public class ModoNormal implements IModoTrabajo {
    @Override
    public boolean esVago() {
        return false;
    }
    @Override
    public void accionAlDelegar(EncargadoAbstracto encargado, IExcusa excusa) {

    }
}
