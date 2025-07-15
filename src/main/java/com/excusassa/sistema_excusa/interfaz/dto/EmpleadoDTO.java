package com.excusassa.sistema_excusa.interfaz.dto;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import lombok.Data;

@Data
public class EmpleadoDTO {
    private String nombre;
    private String email;
    private int nroLegajo;

    public static EmpleadoDTO fromEntity(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNombre(empleado.getNombre());
        dto.setEmail(empleado.getEmail());
        dto.setNroLegajo(empleado.getNroLegajo());
        return dto;
    }
}