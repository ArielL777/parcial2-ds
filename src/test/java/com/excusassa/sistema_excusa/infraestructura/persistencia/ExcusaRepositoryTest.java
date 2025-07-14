package com.excusassa.sistema_excusa.infraestructura.persistencia;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.TipoExcusa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ExcusaRepositoryTest {

    @Autowired
    private ExcusaRepository excusaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    void alGuardarUnaExcusa_deberiaPersistirseCorrectamente() {

        Empleado empleado = empleadoRepository.save(new Empleado("Carlos Test", "carlos.test@email.com", 5001));

        Excusa excusaNueva = new Excusa(TipoExcusa.CORTE_LUZ, "Sin luz en el barrio", empleado);

        Excusa excusaGuardada = excusaRepository.save(excusaNueva);

        assertNotNull(excusaGuardada.getId(), "La excusa guardada debería tener un ID.");
        assertEquals(TipoExcusa.CORTE_LUZ, excusaGuardada.getTipo(), "El tipo de la excusa no es el correcto.");
        assertEquals(empleado.getId(), excusaGuardada.getEmpleado().getId(), "La excusa no está asociada al empleado correcto.");
    }

    @Test
    void findByEmpleadoNroLegajo_deberiaDevolverSoloExcusasDeEseEmpleado() {

        Empleado empleado1 = empleadoRepository.save(new Empleado("Empleado Uno", "uno@email.com", 1111));
        Empleado empleado2 = empleadoRepository.save(new Empleado("Empleado Dos", "dos@email.com", 2222));

        excusaRepository.save(new Excusa(TipoExcusa.INVEROSIMIL, "Excusa de empleado 1", empleado1));
        excusaRepository.save(new Excusa(TipoExcusa.PERDI_TRANSPORTE, "Excusa A de empleado 2", empleado2));
        excusaRepository.save(new Excusa(TipoExcusa.ME_QUEDE_DORMIDO, "Excusa B de empleado 2", empleado2));

        List<Excusa> excusasEncontradas = excusaRepository.findByEmpleadoNroLegajo(2222);

        assertEquals(2, excusasEncontradas.size(), "Debería haber encontrado 2 excusas para el legajo 2222.");

        assertTrue(excusasEncontradas.stream().allMatch(excusa -> excusa.getEmpleado().getId().equals(empleado2.getId())));
    }
}