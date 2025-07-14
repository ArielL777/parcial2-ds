package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.TipoExcusa;
import com.excusassa.sistema_excusa.interfaz.dto.ExcusaRequestDTO;
import com.excusassa.sistema_excusa.servicios.excusa.ExcusaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        ExcusaRequestDTO excusaRequest = new ExcusaRequestDTO("me quedé dormido", "La alarma no sonó", 1001);
        String excusaJson = objectMapper.writeValueAsString(excusaRequest);

        Empleado empleadoFalso = new Empleado("Test", "test@test.com", 1001);
        Excusa excusaDevueltaPorMock = new Excusa(TipoExcusa.ME_QUEDE_DORMIDO, "La alarma no sonó", empleadoFalso);

        when(excusaService.crearYProcesarExcusa(any(ExcusaRequestDTO.class))).thenReturn(excusaDevueltaPorMock);

        mockMvc.perform(post("/api/excusas/presentar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(excusaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo", is("ME_QUEDE_DORMIDO")));
    }

    @Test
    void alPresentarExcusa_cuandoServicioLanzaExcepcion_deberiaDevolverBadRequest() throws Exception {
        ExcusaRequestDTO excusaRequest = new ExcusaRequestDTO("Motivo inválido", "Descripción", 9999);
        String excusaJson = objectMapper.writeValueAsString(excusaRequest);

        when(excusaService.crearYProcesarExcusa(any(ExcusaRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("El motivo y el legajo del empleado son obligatorios."));

        mockMvc.perform(post("/api/excusas/presentar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(excusaJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El motivo y el legajo del empleado son obligatorios."));
    }

    @Test
    void alPedirTodasLasExcusas_deberiaDevolverListaConExcusas() throws Exception {

        Empleado empleadoFalso = new Empleado("Test", "test@test.com", 1001);

        List<Excusa> listaFalsa = List.of(new Excusa(TipoExcusa.CORTE_LUZ, "Sin luz", empleadoFalso));

        when(excusaService.obtenerTodas()).thenReturn(listaFalsa);


        mockMvc.perform(get("/api/excusas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tipo", is("CORTE_LUZ")));
    }

    @Test
    void alPedirExcusasPorLegajo_deberiaDevolverSoloLasDeEseEmpleado() throws Exception {
        Integer legajoBuscado = 123;
        Empleado empleadoFalso = new Empleado("Empleado Correcto", "test@test.com", legajoBuscado);

        List<Excusa> listaFalsa = List.of(new Excusa(TipoExcusa.PERDI_TRANSPORTE, "Se fue", empleadoFalso));

        when(excusaService.obtenerPorLegajo(legajoBuscado)).thenReturn(listaFalsa);

        mockMvc.perform(get("/api/excusas/{legajo}", legajoBuscado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].empleado.nroLegajo", is(legajoBuscado)));
    }
}