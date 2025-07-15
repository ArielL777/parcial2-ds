package com.excusassa.sistema_excusa.interfaz.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EmpleadoRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @NotNull(message = "El número de legajo es obligatorio")
        @Positive(message = "El número de legajo debe ser un número positivo")
        Integer nroLegajo
) {
}