package com.excusassa.dominio.modelo.excusa;
import com.excusassa.dominio.modelo.empleado.Empleado;

public class ExcusaInverosimil extends ExcusaAbstracta {
    public ExcusaInverosimil(String motivo, String descripcion, Empleado empleado) {
        super(motivo, descripcion, empleado);
    }

    @Override public boolean procesablePorCEO() { return true; }
}
