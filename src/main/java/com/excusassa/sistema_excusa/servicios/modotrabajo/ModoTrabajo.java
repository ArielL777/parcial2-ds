package com.excusassa.sistema_excusa.servicios.modotrabajo;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.servicios.encargado.EncargadoAbstracto;

public enum ModoTrabajo {

    NORMAL {
        @Override
        public void procesar(EncargadoAbstracto encargado, Excusa excusa) {
            if (encargado.puedeProcesar(excusa)) {
                encargado.procesarExcusaInterna(excusa);
            } else {
                encargado.delegarASiguiente(excusa);
            }
        }
    },
    VAGO {
        @Override
        public void procesar(EncargadoAbstracto encargado, Excusa excusa) {
            encargado.delegarASiguiente(excusa);
        }
    },
    PRODUCTIVO {
        @Override
        public void procesar(EncargadoAbstracto encargado, Excusa excusa) {
            if (encargado.puedeProcesar(excusa)) {
                encargado.procesarExcusaInterna(excusa);
            } else {
                encargado.getEmailSender().enviarEmail(
                        "cto@excusassa.com",
                        encargado.getEmail(),
                        "Excusa Derivada por Productividad",
                        "El encargado " + encargado.getNombre() + " derivó una excusa de tipo: " + excusa.getTipo().getMotivo()
                );
                encargado.delegarASiguiente(excusa);
            }
        }
    };
    public abstract void procesar(EncargadoAbstracto encargado, Excusa excusa);
}