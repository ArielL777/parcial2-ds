package com.excusassa.sistema_excusa.infraestructura.persistencia;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProntuarioRepositoryTest {

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    void alGuardarUnProntuario_deberiaPersistirseCorrectamente() {

        Empleado empleado = empleadoRepository.save(new Empleado("Empleado Con Prontuario", "pront@email.com", 7777));

        Prontuario prontuarioNuevo = new Prontuario(
                empleado,
                "Fui abducido por aliens",
                empleado.getNroLegajo(),
                new Date()
        );

        prontuarioRepository.save(prontuarioNuevo);

        List<Prontuario> prontuarios = prontuarioRepository.findAll();

        assertEquals(1, prontuarios.size(), "Debería haber un prontuario en la base de datos.");
        assertEquals("Fui abducido por aliens", prontuarios.get(0).getMotivoExcusa());
        assertEquals(empleado.getId(), prontuarios.get(0).getEmpleado().getId());
    }
}