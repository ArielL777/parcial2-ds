package com.excusassa.dominio.modelo.empleado;

public class Empleado {
    protected String nombre;
    protected String email;
    protected int nroLegajo;

    public Empleado(String nombre, String email, int nroLegajo) {
        this.nombre = nombre;
        this.email = email;
        this.nroLegajo = nroLegajo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getNroLegajo() {
        return nroLegajo;
    }
}