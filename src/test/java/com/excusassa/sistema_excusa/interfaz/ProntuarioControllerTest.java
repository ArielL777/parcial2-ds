package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import com.excusassa.sistema_excusa.servicios.prontuario.ProntuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(ProntuarioController.class)
class ProntuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProntuarioService prontuarioService;

    @Test
    void alPedirProntuarios_deberiaDevolverListaVacia_siNoHayProntuarios() throws Exception {
        when(prontuarioService.obtenerTodos()).thenReturn(List.of());

        mockMvc.perform(get("/api/prontuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void alPedirProntuarios_cuandoExisten_deberiaDevolverListaConDatos() throws Exception {
        Empleado empleadoFalso = new Empleado("Empleado CEO", "ceo@test.com", 999);
        List<Prontuario> listaFalsa = List.of(new Prontuario(empleadoFalso, "Abducido por aliens", 999, new java.util.Date()));

        when(prontuarioService.obtenerTodos()).thenReturn(listaFalsa);

        mockMvc.perform(get("/api/prontuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].motivoExcusa", is("Abducido por aliens")));
    }
}