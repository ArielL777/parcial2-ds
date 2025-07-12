package com.excusassa.sistema_excusa.dominio.modelo.excusa;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;

public abstract class ExcusaAbstracta implements IExcusa {
    protected String motivo;
    protected String descripcion;
    protected Empleado empleado;

    public ExcusaAbstracta(String motivo, String descripcion, Empleado empleado) {
        this.motivo = motivo;
        this.descripcion = descripcion;
        this.empleado = empleado;
    }

    @Override
    public String getMotivo() {
        return motivo;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public Empleado getEmpleado() {
        return empleado;
    }


}
