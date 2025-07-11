package com.excusassa.dominio.modelo.excusa;
import com.excusassa.dominio.modelo.empleado.Empleado;

public class ExcusaCompleja extends ExcusaAbstracta {
    public ExcusaCompleja(String motivo, String descripcion, Empleado empleado) {
        super(motivo, descripcion, empleado);
    }

    @Override public boolean procesablePorGerente() { return true; }
}
