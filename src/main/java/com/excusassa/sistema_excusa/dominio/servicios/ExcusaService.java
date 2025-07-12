package com.excusassa.sistema_excusa.dominio.servicios;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.interfaz.dto.ExcusaRequestDTO;
import com.excusassa.sistema_excusa.dominio.servicios.encargado.CadenaEncargados;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import com.excusassa.sistema_excusa.infraestructura.excepciones.RecursoNoEncontradoException;


import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExcusaService {

    private final CadenaEncargados cadenaEncargados;
    private final ExcusaFactory excusaFactory;
    private Map<Integer, Empleado> empleadosEnMemoria;

    public ExcusaService(CadenaEncargados cadenaEncargados, ExcusaFactory excusaFactory) {
        this.cadenaEncargados = cadenaEncargados;
        this.excusaFactory = excusaFactory;
    }

    @PostConstruct
    private void inicializarDatos() {
        this.empleadosEnMemoria = Stream.of(
                new Empleado("Juan Perez", "juan@excusassa.com", 1001),
                new Empleado("Ana Gomez", "ana@excusassa.com", 1002),
                new Empleado("Pedro Rodriguez", "pedro@excusassa.com", 1007)
        ).collect(Collectors.toMap(Empleado::getNroLegajo, Function.identity()));
    }


    public IExcusa crearYProcesarExcusa(ExcusaRequestDTO dto) throws BadRequestException {

        if (dto.motivo() == null || dto.motivo().isEmpty() || dto.legajoEmpleado() == null) {
            throw new BadRequestException("El motivo y el legajo del empleado son obligatorios.");
        }

        Empleado empleado = Optional.ofNullable(empleadosEnMemoria.get(dto.legajoEmpleado()))
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado con legajo: " + dto.legajoEmpleado()));

        IExcusa excusa = excusaFactory.crear(dto.motivo(), dto.descripcion(), empleado);

        cadenaEncargados.procesarExcusa(excusa);

        return excusa;
    }
}