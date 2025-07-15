package com.excusassa.sistema_excusa.interfaz;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.servicios.empleado.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.excusassa.sistema_excusa.interfaz.dto.EmpleadoRequestDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmpleadoService empleadoService;

    @Test
    void alPedirTodosLosEmpleados_deberiaDevolverListaYStatusOK() throws Exception {
        List<Empleado> listaFalsa = List.of(
                new Empleado("Juan Perez", "juan@excusassa.com", 1001),
                new Empleado("Ana Gomez", "ana@excusassa.com", 1002)
        );

        when(empleadoService.obtenerTodosLosEmpleados()).thenReturn(listaFalsa);

        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Juan Perez")))
                .andExpect(jsonPath("$[1].nroLegajo", is(1002)));
    }

    @Test
    void alCrearUnEmpleadoValido_deberiaDevolverEmpleadoDTOyStatusCreated() throws Exception {

        EmpleadoRequestDTO requestDTO = new EmpleadoRequestDTO("Nuevo Empleado", "nuevo@email.com", 5555);

        Empleado empleadoGuardado = new Empleado("Nuevo Empleado", "nuevo@email.com", 5555);
        empleadoGuardado.setId(1);

        when(empleadoService.crearEmpleado(any(EmpleadoRequestDTO.class))).thenReturn(empleadoGuardado);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is("Nuevo Empleado")))
                .andExpect(jsonPath("$.email", is("nuevo@email.com")));
    }

    @Test
    void alCrearUnEmpleadoConNombreVacio_deberiaDevolverBadRequest() throws Exception {

        EmpleadoRequestDTO requestDTOInvalido = new EmpleadoRequestDTO("", "email@valido.com", 5556);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTOInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.nombre", is("El nombre es obligatorio")));
    }
}