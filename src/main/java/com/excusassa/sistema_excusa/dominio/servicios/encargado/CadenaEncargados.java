package com.excusassa.sistema_excusa.dominio.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class CadenaEncargados {

    // Se mantiene igual. Es el punto de entrada a la cadena.
    private final IEncargado primerEncargado;

    // ESTE ES EL GRAN CAMBIO.
    // Ya no recibe los "materiales" (modoTrabajo, emailSender).
    // Ahora recibe el producto terminado (el primer encargado) que fue
    // ensamblado en la clase CadenaDeResponsabilidadConfig.
    public CadenaEncargados(@Qualifier("primerEncargado") IEncargado primerEncargado) {
        this.primerEncargado = primerEncargado;
    }

    // Este método se mantiene igual. Su lógica no cambia.
    public void procesarExcusa(IExcusa excusa) {
        primerEncargado.manejarExcusa(excusa);
    }

    // El método privado construirCadena() se ELIMINA por completo.
    // Esa responsabilidad ahora la tiene la clase de configuración.
}
