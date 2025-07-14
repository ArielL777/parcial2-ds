package com.excusassa.sistema_excusa.infraestructura.persistencia;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class ExcusaRepository {

    private final List<IExcusa> excusas = new CopyOnWriteArrayList<>();

    public void save(IExcusa excusa) {
        excusas.add(excusa);
    }

    public List<IExcusa> findAll() {
        return new ArrayList<>(excusas);
    }

    public List<IExcusa> findByLegajoEmpleado(Integer legajo) {
        return excusas.stream()
                .filter(excusa -> excusa.getEmpleado().getNroLegajo() == legajo)
                .collect(Collectors.toList());
    }
}