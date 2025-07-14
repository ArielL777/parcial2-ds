package com.excusassa.sistema_excusa.dominio.servicios.encargado;


import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.ExcusaInverosimil;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.servicios.encargado.CadenaEncargados;
import com.excusassa.sistema_excusa.servicios.notificacion.NotificadorCEO;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSenderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CadenaEncargadosTest {

    private CadenaEncargados cadena;

    @BeforeEach
    void setUp() {
      //  AdministradorProntuarios.resetInstance();
        NotificadorCEO.resetInstance();
        EmailSender emailSender = new EmailSenderImpl();
        //cadena = new CadenaEncargados(new ModoNormal(), emailSender);
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

      //  AdministradorProntuarios admin = AdministradorProntuarios.getInstance();
      //  assertEquals(1, admin.getProntuarios().size(), "Debería haberse creado un único prontuario.");

       // Prontuario prontuarioCreado = admin.getProntuarios().getFirst();
       // assertEquals("Valentina", prontuarioCreado.getEmpleado().getNombre(), "El prontuario debe ser del empleado correcto.");
       // assertEquals(excusa, prontuarioCreado.getExcusa(), "La excusa en el prontuario debe ser la correcta.");
    }
}