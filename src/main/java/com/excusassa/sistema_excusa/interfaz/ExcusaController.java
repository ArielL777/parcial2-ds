package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.interfaz.dto.ExcusaRequestDTO;
import com.excusassa.sistema_excusa.dominio.servicios.ExcusaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/excusas")
public class ExcusaController {

    @Autowired
    private ExcusaService excusaService;

    @PostMapping("/presentar")
    public ResponseEntity<?> presentarExcusa(@RequestBody ExcusaRequestDTO excusaDTO) {
        try {
            IExcusa excusaProcesada = excusaService.crearYProcesarExcusa(excusaDTO);
            return ResponseEntity.ok(excusaProcesada);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}