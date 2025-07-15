package com.excusassa.sistema_excusa.interfaz.dto;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;
import lombok.Data;

@Data
public class EncargadoDTO {
    private String nombre;
    private String email;
    private int nroLegajo;
    private String modoTrabajo;

    public static EncargadoDTO fromEntity(Empleado encargado, ModoTrabajo modo) {
        EncargadoDTO dto = new EncargadoDTO();
        dto.setNombre(encargado.getNombre());
        dto.setEmail(encargado.getEmail());
        dto.setNroLegajo(encargado.getNroLegajo());
        dto.setModoTrabajo(modo.name());
        return dto;
    }
}