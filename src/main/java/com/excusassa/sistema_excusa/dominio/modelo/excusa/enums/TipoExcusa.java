package com.excusassa.sistema_excusa.dominio.modelo.excusa.enums;

import java.util.Arrays;

public enum TipoExcusa {

    ME_QUEDE_DORMIDO("me quedé dormido") {
        @Override public boolean esTrivial() { return true; }
    },
    PERDI_TRANSPORTE("perdí el colectivo") {
        @Override public boolean esTrivial() { return true; }
    },
    CORTE_LUZ("se cortó la luz") {
        @Override public boolean esModerada() { return true; }
    },
    CUIDADO_FAMILIAR("cuidar a un familiar") {
        @Override public boolean esModerada() { return true; }
    },
    ABDUCIDO_POR_ALIENS("fui abducido por aliens") {
        @Override public boolean esCompleja() { return true; }
    },
    PALOMA_ROBO_BICICLETA("una paloma robó mi bicicleta") {
        @Override public boolean esCompleja() { return true; }
    },
    INVEROSIMIL("inverosimil") {
        @Override public boolean esInverosimil() { return true; }
    };

    private final String motivo;

    TipoExcusa(String motivo) {
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public boolean esTrivial() { return false; }
    public boolean esModerada() { return false; }
    public boolean esCompleja() { return false; }
    public boolean esInverosimil() { return false; }


    public static TipoExcusa fromString(String text) {
        return Arrays.stream(TipoExcusa.values())
                .filter(tipo -> tipo.motivo.equalsIgnoreCase(text))
                .findFirst()
                .orElse(INVEROSIMIL);
    }
}