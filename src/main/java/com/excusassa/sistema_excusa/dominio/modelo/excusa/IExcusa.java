package com.excusassa.sistema_excusa.dominio.modelo.excusa;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.empleado.SupervisorArea;

public interface IExcusa {
    String getMotivo();
    String getDescripcion();
    Empleado getEmpleado();

    default boolean procesablePorRecepcionista() { return false; }
    default boolean procesablePorSupervisor()   { return false; }
    default boolean procesablePorGerente()      { return false; }
    default boolean procesablePorCEO()          { return false; }
    default void notificar(SupervisorArea supervisor) {
    }
}
