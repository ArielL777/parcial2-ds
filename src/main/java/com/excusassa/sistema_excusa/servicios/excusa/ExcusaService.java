package com.excusassa.sistema_excusa.servicios.excusa;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.infraestructura.excepciones.RecursoNoEncontradoException;
import com.excusassa.sistema_excusa.infraestructura.persistencia.EmpleadoRepository;
import com.excusassa.sistema_excusa.interfaz.dto.ExcusaRequestDTO;
import com.excusassa.sistema_excusa.servicios.encargado.CadenaEncargados;
import com.excusassa.sistema_excusa.infraestructura.persistencia.ExcusaRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcusaService {

    private final CadenaEncargados cadenaEncargados;
    private final ExcusaFactory excusaFactory;
    private final EmpleadoRepository empleadoRepository;
    private final ExcusaRepository excusaRepository;

    public ExcusaService(CadenaEncargados cadenaEncargados,
                         ExcusaFactory excusaFactory,
                         EmpleadoRepository empleadoRepository,
                         ExcusaRepository excusaRepository) {
        this.cadenaEncargados = cadenaEncargados;
        this.excusaFactory = excusaFactory;
        this.empleadoRepository = empleadoRepository;
        this.excusaRepository = excusaRepository;
    }

    public IExcusa crearYProcesarExcusa(ExcusaRequestDTO dto) throws BadRequestException {
        if (dto.motivo() == null || dto.motivo().isEmpty() || dto.legajoEmpleado() == null) {
            throw new BadRequestException("El motivo y el legajo del empleado son obligatorios.");
        }

        Empleado empleado = empleadoRepository.findById(dto.legajoEmpleado())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado con legajo: " + dto.legajoEmpleado()));

        IExcusa excusa = excusaFactory.crear(dto.motivo(), dto.descripcion(), empleado);
        cadenaEncargados.procesarExcusa(excusa);
        return excusa;
    }

    public List<IExcusa> obtenerTodas() {
        return excusaRepository.findAll();
    }

    public List<IExcusa> obtenerPorLegajo(Integer legajo) {
        return excusaRepository.findByLegajoEmpleado(legajo);
    }

}