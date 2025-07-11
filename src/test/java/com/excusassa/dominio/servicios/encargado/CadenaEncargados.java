package com.excusassa.dominio.servicios.encargado;


import com.excusassa.dominio.modelo.empleado.Empleado;
import com.excusassa.dominio.modelo.excusa.ExcusaInverosimil;
import com.excusassa.dominio.modelo.excusa.IExcusa;
import com.excusassa.dominio.servicios.modotrabajo.ModoNormal;
import com.excusassa.infraestructura.persistencia.AdministradorProntuarios; // Importante añadir esto
import com.excusassa.dominio.modelo.prontuario.Prontuario; // Y esto también
import com.excusassa.dominio.servicios.notificacion.NotificadorCEO;
import com.excusassa.infraestructura.email.EmailSender;
import com.excusassa.infraestructura.email.EmailSenderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CadenaEncargadosTest {

    private CadenaEncargados cadena;

    @BeforeEach
    void setUp() {
        AdministradorProntuarios.resetInstance();
        NotificadorCEO.resetInstance();
        EmailSender emailSender = new EmailSenderImpl();
        cadena = new CadenaEncargados(new ModoNormal(), emailSender);
    }

    @Test
    void testExcusaInverosimilCreaUnProntuario() {
        Empleado empleado = new Empleado("Valentina", "valentina@excusassa.com", 1007);
        IExcusa excusa = new ExcusaInverosimil(
                "Fui abducida por extraterrestres",
                "Y me devolvieron recién hoy a las 11 AM",
                empleado
        );

        cadena.procesarExcusa(excusa);

        AdministradorProntuarios admin = AdministradorProntuarios.getInstance();
        assertEquals(1, admin.getProntuarios().size(), "Debería haberse creado un único prontuario.");

        Prontuario prontuarioCreado = admin.getProntuarios().getFirst();
        assertEquals("Valentina", prontuarioCreado.getEmpleado().getNombre(), "El prontuario debe ser del empleado correcto.");
        assertEquals(excusa, prontuarioCreado.getExcusa(), "La excusa en el prontuario debe ser la correcta.");
    }
}