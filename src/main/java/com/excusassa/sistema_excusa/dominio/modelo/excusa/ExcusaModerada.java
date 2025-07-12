package com.excusassa.sistema_excusa.dominio.modelo.excusa;
import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.empleado.SupervisorArea;

// Clase intermedia para clasificar excusas moderadas
public abstract class ExcusaModerada extends ExcusaAbstracta {

    public ExcusaModerada(String motivo, String descripcion, Empleado empleado) {
        super(motivo, descripcion, empleado);
    }

    public abstract void notificar(SupervisorArea supervisor);
}
