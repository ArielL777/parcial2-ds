package com.excusassa.sistema_excusa.interfaz.dto;

public record ExcusaRequestDTO(
        String motivo,
        String descripcion,
        Integer legajoEmpleado
        )
    {
}
