package com.excusassa.sistema_excusa.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.TipoExcusa;
import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import com.excusassa.sistema_excusa.infraestructura.persistencia.EmpleadoRepository;
import com.excusassa.sistema_excusa.infraestructura.persistencia.ProntuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CadenaEncargadosTest {

    @Autowired
    private CadenaEncargados cadenaEncargados;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ProntuarioRepository prontuarioRepository;


    @Test
    void alProcesarExcusaInverosimil_deberiaCrearseUnProntuarioEnLaBD() {
        Empleado empleado = empleadoRepository.save(new Empleado("Valentina", "valentina@excusassa.com", 1007));

        Excusa excusa = new Excusa(
                TipoExcusa.INVEROSIMIL,
                "Fui abducida por extraterrestres",
                empleado
        );

        cadenaEncargados.procesarExcusa(excusa);

        List<Prontuario> prontuarios = prontuarioRepository.findAll();

        assertEquals(1, prontuarios.size(), "Debería haberse creado un único prontuario.");
        Prontuario prontuarioCreado = prontuarios.get(0);
        assertEquals("Valentina", prontuarioCreado.getEmpleado().getNombre(), "El prontuario debe ser del empleado correcto.");
        assertEquals("inverosimil", prontuarioCreado.getMotivoExcusa(), "El motivo en el prontuario debe ser el correcto.");
    }
}