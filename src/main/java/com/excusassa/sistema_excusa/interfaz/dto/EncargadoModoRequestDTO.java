package com.excusassa.sistema_excusa.interfaz.dto;

import jakarta.validation.constraints.NotNull;


public record EncargadoModoRequestDTO(
        @NotNull(message = "El legajo del encargado es obligatorio")
        Integer legajo,

        @NotNull(message = "El nuevo modo de trabajo es obligatorio")
        String nuevoModo
) {}