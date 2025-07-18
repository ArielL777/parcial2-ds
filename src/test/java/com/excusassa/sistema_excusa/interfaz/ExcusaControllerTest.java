package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.EstadoExcusa;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.TipoExcusa;
import com.excusassa.sistema_excusa.infraestructura.excepciones.RecursoNoEncontradoException;
import com.excusassa.sistema_excusa.interfaz.dto.ExcusaRequestDTO;
import com.excusassa.sistema_excusa.servicios.excusa.ExcusaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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
        String mensajeError = "El motivo y el legajo del empleado son obligatorios.";

        when(excusaService.crearYProcesarExcusa(any(ExcusaRequestDTO.class)))
                .thenThrow(new IllegalArgumentException(mensajeError));

        mockMvc.perform(post("/api/excusas/presentar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(excusaJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is(mensajeError)));
    }

    @Test
    void alPresentarExcusaConEmpleadoInexistente_deberiaDevolverError404() throws Exception {
        ExcusaRequestDTO excusaRequest = new ExcusaRequestDTO("Un motivo cualquiera", "Descripción", 9999);
        String excusaJson = objectMapper.writeValueAsString(excusaRequest);
        String mensajeError = "Empleado no encontrado con legajo: 9999";

        when(excusaService.crearYProcesarExcusa(any(ExcusaRequestDTO.class)))
                .thenThrow(new RecursoNoEncontradoException(mensajeError));

        mockMvc.perform(post("/api/excusas/presentar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(excusaJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is(mensajeError)));
    }

    @Test
    void alPresentarExcusaConDatosInvalidos_deberiaDevolverError400ConDetalles() throws Exception {

        ExcusaRequestDTO excusaInvalida = new ExcusaRequestDTO("", "Descripción válida", 1001);
        String excusaJson = objectMapper.writeValueAsString(excusaInvalida);

        mockMvc.perform(post("/api/excusas/presentar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(excusaJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors.motivo", containsString("El motivo no puede estar vacío.")))
                .andExpect(jsonPath("$.errors.motivo", containsString("El motivo debe tener al menos 5 caracteres.")));
    }

    @Test
    void alPedirTodasLasExcusas_deberiaDevolverListaConExcusas() throws Exception {
        Empleado empleadoFalso = new Empleado("Test", "test@test.com", 1001);
        List<Excusa> listaFalsa = List.of(new Excusa(TipoExcusa.CORTE_LUZ, "Sin luz", empleadoFalso));

        when(excusaService.obtenerTodas(null, null, null, null)).thenReturn(listaFalsa);

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

    @Test
    void alPedirExcusasConFiltroPorMotivo_deberiaDevolverListaFiltrada() throws Exception {

        Empleado empleadoFalso = new Empleado("Test", "test@test.com", 1001);
        String motivoBuscado = "me quedé dormido";

        List<Excusa> listaFiltrada = List.of(
                new Excusa(TipoExcusa.ME_QUEDE_DORMIDO, "No sonó la alarma", empleadoFalso)
        );

        when(excusaService.obtenerTodas(motivoBuscado, null, null, null))
                .thenReturn(listaFiltrada);

        mockMvc.perform(get("/api/excusas")
                        .param("motivo", motivoBuscado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tipo", is("ME_QUEDE_DORMIDO")));
    }

    @Test
    void alEliminarExcusas_deberiaLlamarAlServicioYDevolverRespuestaCorrecta() throws Exception {

        String fechaLimite = "2025-01-01";

        int cantidadBorradas = 5;

        when(excusaService.eliminarExcusasAntiguas(any(Date.class)))
                .thenReturn(cantidadBorradas);

        mockMvc.perform(delete("/api/excusas/eliminar")
                        .param("fechaLimite", fechaLimite))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Operación de borrado completada.")))
                .andExpect(jsonPath("$.excusasEliminadas", is(5)));
    }

    @Test
    void alPedirExcusasRechazadas_deberiaDevolverListaCorrecta() throws Exception {

        Empleado empleadoFalso = new Empleado("Test", "test@test.com", 1001);
        Excusa excusaRechazada = new Excusa(TipoExcusa.INVEROSIMIL, "Motivo rechazado", empleadoFalso);
        excusaRechazada.setEstado(EstadoExcusa.RECHAZADA);

        List<Excusa> listaFalsa = List.of(excusaRechazada);

        when(excusaService.obtenerExcusasRechazadas()).thenReturn(listaFalsa);

        mockMvc.perform(get("/api/excusas/rechazadas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].estado", is("RECHAZADA")));
    }

    @Test
    void alBuscarPorLegajoYFechas_deberiaDevolverListaFiltrada() throws Exception {
        Integer legajoBuscado = 123;
        String fechaDesdeStr = "2025-07-01";
        String fechaHastaStr = "2025-07-31";

        Empleado empleadoFalso = new Empleado("Empleado Filtrado", "filtrado@test.com", legajoBuscado);
        List<Excusa> listaFiltrada = List.of(new Excusa(TipoExcusa.CORTE_LUZ, "Excusa en fecha", empleadoFalso));

        when(excusaService.buscarPorLegajoYFechas(eq(legajoBuscado), any(Date.class), any(Date.class)))
                .thenReturn(listaFiltrada);

        mockMvc.perform(get("/api/excusas/busqueda")
                        .param("legajo", legajoBuscado.toString())
                        .param("fechaDesde", fechaDesdeStr)
                        .param("fechaHasta", fechaHastaStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].empleado.nroLegajo", is(legajoBuscado)));
    }
}