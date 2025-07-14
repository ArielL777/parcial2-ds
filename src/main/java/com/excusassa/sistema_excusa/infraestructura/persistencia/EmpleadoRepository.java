package com.excusassa.sistema_excusa.infraestructura.persistencia;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class EmpleadoRepository {

    private Map<Integer, Empleado> empleadosEnMemoria;

    @PostConstruct
    private void inicializarDatos() {
        this.empleadosEnMemoria = Stream.of(
                new Empleado("Juan Perez", "juan@excusassa.com", 1001),
                new Empleado("Ana Gomez", "ana@excusassa.com", 1002),
                new Empleado("Pedro Rodriguez", "pedro@excusassa.com", 1007)
        ).collect(Collectors.toMap(Empleado::getNroLegajo, Function.identity()));
    }


    public Optional<Empleado> findById(Integer legajo) {
        return Optional.ofNullable(empleadosEnMemoria.get(legajo));
    }

    public List<Empleado> findAll() {
        return new ArrayList<>(empleadosEnMemoria.values());
    }

}