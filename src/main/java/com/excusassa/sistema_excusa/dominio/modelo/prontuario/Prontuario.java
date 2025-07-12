package com.excusassa.sistema_excusa.dominio.modelo.prontuario;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;

import java.util.Date;

public class Prontuario {
    private final Empleado empleado;
    private final IExcusa excusa;
    private final int nroLegajo;
    private final Date fecha;

    public Prontuario(Empleado empleado, IExcusa excusa, int nroLegajo, Date fecha) {
        this.empleado = empleado;
        this.excusa = excusa;
        this.nroLegajo = nroLegajo;
        this.fecha = fecha;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public IExcusa getExcusa() {
        return excusa;
    }

    public int getNroLegajo() {
        return nroLegajo;
    }

    public Date getFecha() {
        return fecha;
    }
}
