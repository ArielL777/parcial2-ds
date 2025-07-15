package com.excusassa.sistema_excusa.interfaz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExcusaRequestDTO(

        @NotBlank(message = "El motivo no puede estar vacío.")
        @Size(min = 5, message = "El motivo debe tener al menos 5 caracteres.")
        String motivo,
        String descripcion,
        @NotNull(message = "El legajo del empleado no puede ser nulo.")
        Integer legajoEmpleado
        )
{}