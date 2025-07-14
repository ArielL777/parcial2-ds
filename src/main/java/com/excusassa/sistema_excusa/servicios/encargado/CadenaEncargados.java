package com.excusassa.sistema_excusa.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo. excusa.IExcusa;
import com.excusassa.sistema_excusa.infraestructura.persistencia.ExcusaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CadenaEncargados {

    private final IEncargado primerEncargado;
    private final ExcusaRepository excusaRepository;

    public CadenaEncargados(@Qualifier("primerEncargado") IEncargado primerEncargado,
                            ExcusaRepository excusaRepository) {
        this.primerEncargado = primerEncargado;
        this.excusaRepository = excusaRepository;
    }

    public void procesarExcusa(IExcusa excusa) {
        primerEncargado.manejarExcusa(excusa);
        excusaRepository.save(excusa);
    }
}