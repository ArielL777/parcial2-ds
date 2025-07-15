package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.interfaz.dto.EncargadoDTO;
import com.excusassa.sistema_excusa.interfaz.dto.EncargadoModoRequestDTO;
import com.excusassa.sistema_excusa.servicios.encargado.CadenaAdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import com.excusassa.sistema_excusa.interfaz.dto.EncargadoRequestDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class EncargadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CadenaAdminService cadenaAdminService;

    @Test
    void alCambiarModoDeEncargado_deberiaActualizarseEnLaCadena() throws Exception {

        int legajoSupervisor = 1002;
        EncargadoModoRequestDTO requestDTO = new EncargadoModoRequestDTO(legajoSupervisor, "VAGO");
        String requestJson = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(put("/api/encargados/modo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Modo de trabajo actualizado correctamente.")));

        List<EncargadoDTO> configuracionActualizada = cadenaAdminService.obtenerConfiguracionCadena();

        Optional<EncargadoDTO> supervisorActualizado = configuracionActualizada.stream()
                .filter(e -> e.getNroLegajo() == legajoSupervisor)
                .findFirst();

        assertEquals("VAGO", supervisorActualizado.get().getModoTrabajo(), "El modo de trabajo del supervisor no se actualizó.");
    }

    @Test
    void alAgregarUnNuevoEncargado_laCadenaDeberiaCrecer() throws Exception {

        List<EncargadoDTO> cadenaInicial = cadenaAdminService.obtenerConfiguracionCadena();
        assertEquals(4, cadenaInicial.size());

        EncargadoRequestDTO nuevoEncargadoDTO = new EncargadoRequestDTO(
                "Coach Motivacional",
                "coach@email.com",
                2001,
                "TRIVIAL"
        );

        mockMvc.perform(post("/api/encargados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoEncargadoDTO)))
                .andExpect(status().isCreated());

        List<EncargadoDTO> cadenaActualizada = cadenaAdminService.obtenerConfiguracionCadena();

        assertEquals(5, cadenaActualizada.size());

        assertEquals("Lucía (CEO)", cadenaActualizada.get(3).getNombre());

        EncargadoDTO coach = cadenaActualizada.get(4);
        assertEquals("Coach Motivacional", coach.getNombre());
    }
}