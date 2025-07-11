package com.excusassa.dominio.servicios.modotrabajo;

import com.excusassa.dominio.modelo.excusa.IExcusa;
import com.excusassa.dominio.servicios.encargado.EncargadoAbstracto;

public interface IModoTrabajo {
    boolean esVago();
    void accionAlDelegar(EncargadoAbstracto encargado, IExcusa excusa);
}
