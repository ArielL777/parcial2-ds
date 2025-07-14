package com.excusassa.sistema_excusa.infraestructura.persistencia;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    void alGuardarUnEmpleado_deberiaPoderEncontrarloPorId() {

        Empleado empleadoNuevo = new Empleado("Juan Test", "juan.test@excusassa.com", 9001);

        Empleado empleadoGuardado = empleadoRepository.save(empleadoNuevo);
        assertNotNull(empleadoGuardado.getId());

        Optional<Empleado> empleadoEncontrado = empleadoRepository.findById(empleadoGuardado.getId());

        assertTrue(empleadoEncontrado.isPresent(), "El empleado debería haber sido encontrado en la base de datos");
        assertEquals("Juan Test", empleadoEncontrado.get().getNombre(), "El nombre del empleado encontrado no coincide");
    }

    @Test
    void alGuardarVariosEmpleados_findAllDeberiaDevolverlosTodos() {

        Empleado empleado1 = new Empleado("Ana Test", "ana.test@excusassa.com", 9002);
        Empleado empleado2 = new Empleado("Luis Test", "luis.test@excusassa.com", 9003);
        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado2);

        List<Empleado> todosLosEmpleados = empleadoRepository.findAll();

        assertEquals(2, todosLosEmpleados.size(), "Debería haber 2 empleados en la base de datos");
    }
}