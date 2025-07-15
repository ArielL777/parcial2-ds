package com.excusassa.sistema_excusa.interfaz.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EncargadoRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El email es obligatorio")
        @Email
        String email,

        @NotNull
        @Positive
        Integer nroLegajo,

        @NotBlank
        String tipoExcusasQueManeja
) {}