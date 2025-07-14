package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.infraestructura.excepciones.RecursoNoEncontradoException;
import com.excusassa.sistema_excusa.interfaz.dto.ExcusaRequestDTO;
import com.excusassa.sistema_excusa.servicios.empleado.EmpleadoService;
import com.excusassa.sistema_excusa.servicios.excusa.ExcusaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/excusas")
public class ExcusaController {

    @Autowired
    private ExcusaService excusaService;
    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping("/presentar")
    public ResponseEntity<?> presentarExcusa(@RequestBody ExcusaRequestDTO excusaDTO) {
        try {
            Excusa excusaProcesada = excusaService.crearYProcesarExcusa(excusaDTO);
            return ResponseEntity.ok(excusaProcesada);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Excusa>> obtenerTodasLasExcusas() {
        List<Excusa> excusas = excusaService.obtenerTodas();
        return ResponseEntity.ok(excusas);
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<List<Excusa>> obtenerExcusasPorLegajo(@PathVariable Integer legajo) {
        List<Excusa> excusas = excusaService.obtenerPorLegajo(legajo);
        return ResponseEntity.ok(excusas);
    }
}