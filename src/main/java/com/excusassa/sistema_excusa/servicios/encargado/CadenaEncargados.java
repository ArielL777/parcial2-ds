package com.excusassa.sistema_excusa.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.infraestructura.persistencia.ExcusaRepository;
import org.springframework.stereotype.Component;

@Component
public class CadenaEncargados {

    private final CadenaManager cadenaManager;
    private final ExcusaRepository excusaRepository;

    public CadenaEncargados(CadenaManager cadenaManager, ExcusaRepository excusaRepository) {
        this.cadenaManager = cadenaManager;
        this.excusaRepository = excusaRepository;
    }

    public void procesarExcusa(Excusa excusa) {
        if (cadenaManager.getPrimerEncargado() != null) {
            cadenaManager.getPrimerEncargado().manejarExcusa(excusa);
        }
        excusaRepository.save(excusa);
    }
}