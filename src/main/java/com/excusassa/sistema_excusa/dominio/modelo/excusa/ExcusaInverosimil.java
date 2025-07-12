package com.excusassa.sistema_excusa.dominio.modelo.excusa;
import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;

public class ExcusaInverosimil extends ExcusaAbstracta {
    public ExcusaInverosimil(String motivo, String descripcion, Empleado empleado) {
        super(motivo, descripcion, empleado);
    }

    @Override public boolean procesablePorCEO() { return true; }
}
