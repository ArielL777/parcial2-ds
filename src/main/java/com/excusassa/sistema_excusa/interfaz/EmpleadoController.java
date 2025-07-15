package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.interfaz.dto.EmpleadoDTO;
import com.excusassa.sistema_excusa.interfaz.dto.EmpleadoRequestDTO;
import com.excusassa.sistema_excusa.servicios.empleado.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> obtenerTodosLosEmpleados() {
        return ResponseEntity.ok(empleadoService.obtenerTodosLosEmpleados());
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@Valid @RequestBody EmpleadoRequestDTO requestDTO) {

        Empleado empleadoCreado = empleadoService.crearEmpleado(requestDTO);

        EmpleadoDTO responseDTO = EmpleadoDTO.fromEntity(empleadoCreado);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}