package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.interfaz.dto.ExcusaRequestDTO;
import com.excusassa.sistema_excusa.servicios.excusa.ExcusaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/excusas")
public class ExcusaController {

    @Autowired
    private ExcusaService excusaService;

    public ExcusaController() {}

    @PostMapping("/presentar")
    public ResponseEntity<Excusa> presentarExcusa(@Valid @RequestBody ExcusaRequestDTO excusaDTO) {
        Excusa excusaProcesada = excusaService.crearYProcesarExcusa(excusaDTO);
        return ResponseEntity.ok(excusaProcesada);
    }

    @GetMapping
    public ResponseEntity<List<Excusa>> obtenerTodasLasExcusas(
            @RequestParam(required = false) String motivo,
            @RequestParam(required = false) String encargado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaHasta
    ) {
        List<Excusa> excusas = excusaService.obtenerTodas(motivo, encargado, fechaDesde, fechaHasta);
        return ResponseEntity.ok(excusas);
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<List<Excusa>> obtenerExcusasPorLegajo(@PathVariable Integer legajo) {
        List<Excusa> excusas = excusaService.obtenerPorLegajo(legajo);
        return ResponseEntity.ok(excusas);
    }

    @GetMapping("/busqueda")
    public ResponseEntity<List<Excusa>> buscarExcusas(
            @RequestParam Integer legajo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaHasta
    ) {
        List<Excusa> excusas = excusaService.buscarPorLegajoYFechas(legajo, fechaDesde, fechaHasta);
        return ResponseEntity.ok(excusas);
    }

    @GetMapping("/rechazadas")
    public ResponseEntity<List<Excusa>> obtenerExcusasRechazadas() {
        List<Excusa> excusasRechazadas = excusaService.obtenerExcusasRechazadas();
        return ResponseEntity.ok(excusasRechazadas);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Map<String, Object>> eliminarExcusas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaLimite
    ) {
        int excusasEliminadas = excusaService.eliminarExcusasAntiguas(fechaLimite);

        Map<String, Object> response = Map.of(
                "mensaje", "Operación de borrado completada.",
                "excusasEliminadas", excusasEliminadas
        );

        return ResponseEntity.ok(response);
    }
}