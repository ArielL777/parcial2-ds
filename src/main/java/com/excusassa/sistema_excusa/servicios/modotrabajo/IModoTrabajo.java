package com.excusassa.sistema_excusa.servicios.modotrabajo;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.servicios.encargado.EncargadoAbstracto;

public interface IModoTrabajo {
    boolean esVago();
    void accionAlDelegar(EncargadoAbstracto encargado, IExcusa excusa);
}
