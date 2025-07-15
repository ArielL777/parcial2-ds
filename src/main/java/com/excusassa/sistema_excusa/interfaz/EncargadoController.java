package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.interfaz.dto.EncargadoDTO;
import com.excusassa.sistema_excusa.interfaz.dto.EncargadoModoRequestDTO;
import com.excusassa.sistema_excusa.interfaz.dto.EncargadoRequestDTO;
import com.excusassa.sistema_excusa.servicios.encargado.CadenaAdminService;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/encargados")
public class EncargadoController {

    private final CadenaAdminService cadenaAdminService;

    public EncargadoController(CadenaAdminService cadenaAdminService) {
        this.cadenaAdminService = cadenaAdminService;
    }

    @GetMapping
    public ResponseEntity<List<EncargadoDTO>> obtenerConfiguracionDeLaCadena() {
        List<EncargadoDTO> configuracion = cadenaAdminService.obtenerConfiguracionCadena();
        return ResponseEntity.ok(configuracion);
    }

    @PutMapping("/modo")
    public ResponseEntity<?> cambiarModoDeTrabajo(@Valid @RequestBody EncargadoModoRequestDTO request) {
        try {

            ModoTrabajo nuevoModo = ModoTrabajo.valueOf(request.nuevoModo().toUpperCase());

            boolean exito = cadenaAdminService.cambiarModoDeEncargado(request.legajo(), nuevoModo);

            if (exito) {
                return ResponseEntity.ok().body(Map.of("mensaje", "Modo de trabajo actualizado correctamente."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Encargado no encontrado con legajo: " + request.legajo()));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Modo de trabajo '" + request.nuevoModo() + "' no es válido."));
        }
    }

    @PostMapping
    public ResponseEntity<Void> agregarEncargado(@Valid @RequestBody EncargadoRequestDTO requestDTO) {
        cadenaAdminService.agregarNuevoEncargado(requestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}