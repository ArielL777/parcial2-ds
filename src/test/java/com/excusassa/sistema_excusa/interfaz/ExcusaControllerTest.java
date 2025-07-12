package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.ExcusaTrivial;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.interfaz.dto.ExcusaRequestDTO;
import com.excusassa.sistema_excusa.dominio.servicios.ExcusaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExcusaController.class)
class ExcusaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private ExcusaService excusaService;


    @Test
    void alPresentarUnaExcusaValida_deberiaDevolverElObjetoExcusaConStatusOk() throws Exception {
        ExcusaRequestDTO excusaRequest = new ExcusaRequestDTO("Me quedé dormido", "...", 1001);
        String excusaJson = objectMapper.writeValueAsString(excusaRequest);

        Empleado empleadoFalso = new Empleado("Test", "test@test.com", 1001);
        IExcusa excusaDevueltaPorMock = new ExcusaTrivial("Me quedé dormido", "Mock", empleadoFalso);

        when(excusaService.crearYProcesarExcusa(any(ExcusaRequestDTO.class))).thenReturn(excusaDevueltaPorMock);

        mockMvc.perform(post("/api/excusas/presentar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(excusaJson))
                .andExpect(status().isOk()) // Verifica que el estado es 200
                .andExpect(jsonPath("$.motivo").value("Me quedé dormido"));
    }

    @Test
    void alPresentarExcusa_cuandoServicioLanzaExcepcion_deberiaDevolverBadRequest() throws Exception {

        ExcusaRequestDTO excusaRequest = new ExcusaRequestDTO("Motivo inválido", "Descripción", 9999);
        String excusaJson = objectMapper.writeValueAsString(excusaRequest);

        doThrow(new BadRequestException("Empleado no encontrado"))
                .when(excusaService).crearYProcesarExcusa(any(ExcusaRequestDTO.class));

        mockMvc.perform(post("/api/excusas/presentar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(excusaJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Empleado no encontrado"));
    }
}