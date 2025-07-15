package com.excusassa.sistema_excusa.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class CadenaManager {

    private final List<IEncargado> encargados;
    @Getter
    private IEncargado primerEncargado;

    public CadenaManager(List<IEncargado> encargadosIniciales) {
        this.encargados = new LinkedList<>(encargadosIniciales);
        reconstruirCadena();
    }

    private void reconstruirCadena() {
        if (encargados.isEmpty()) {
            this.primerEncargado = null;
            return;
        }

        this.primerEncargado = encargados.get(0);
        for (int i = 0; i < encargados.size() - 1; i++) {
            encargados.get(i).setSiguiente(encargados.get(i + 1));
        }
    }

    public void agregarEncargado(IEncargado nuevoEncargado) {
        int posicionInsercion = Math.max(0, encargados.size() - 1);
        encargados.add(posicionInsercion, nuevoEncargado);
        reconstruirCadena();
    }

    public List<IEncargado> getEncargadosActuales() {
        return this.encargados;
    }
}