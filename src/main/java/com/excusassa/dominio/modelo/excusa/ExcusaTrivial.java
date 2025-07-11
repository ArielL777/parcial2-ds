package com.excusassa.dominio.modelo.excusa;
import com.excusassa.dominio.modelo.empleado.Empleado;

public class ExcusaTrivial extends ExcusaAbstracta {
    public ExcusaTrivial(String motivo, String descripcion, Empleado empleado) {
        super(motivo, descripcion, empleado);
    }
    @Override public boolean procesablePorRecepcionista() { return true; }
}
