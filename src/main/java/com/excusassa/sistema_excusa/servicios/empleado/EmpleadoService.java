package com.excusassa.sistema_excusa.servicios.empleado;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.infraestructura.persistencia.EmpleadoRepository;
import org.springframework.stereotype.Service;
import com.excusassa.sistema_excusa.interfaz.dto.EmpleadoRequestDTO;

import java.util.List;

@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    public Empleado crearEmpleado(EmpleadoRequestDTO empleadoDTO) {
        if (empleadoRepository.existsByEmailOrNroLegajo(empleadoDTO.email(), empleadoDTO.nroLegajo())) {
            throw new IllegalArgumentException("Ya existe un empleado con el mismo email o número de legajo.");
        }

        Empleado nuevoEmpleado = new Empleado(
                empleadoDTO.nombre(),
                empleadoDTO.email(),
                empleadoDTO.nroLegajo()
        );

        return empleadoRepository.save(nuevoEmpleado);
    }
}