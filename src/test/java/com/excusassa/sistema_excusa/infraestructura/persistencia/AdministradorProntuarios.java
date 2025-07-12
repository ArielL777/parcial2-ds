package com.excusassa.sistema_excusa.infraestructura.persistencia;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.ExcusaInverosimil;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import com.excusassa.sistema_excusa.dominio.servicios.notificacion.IObserver;
import com.excusassa.sistema_excusa.dominio.servicios.notificacion.NotificadorCEO;
import com.excusassa.sistema_excusa.infraestructura.persistencia.AdministradorProntuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorProntuariosTest {

    @BeforeEach
    void setUp() {
        NotificadorCEO admin = NotificadorCEO.getInstance();
    }

    @Test
    void testCreacionPersistenciaYNotificacion() {
        class StubObserver implements IObserver {
            boolean notificado = false;
            Prontuario recibido = null;

            @Override
            public void actualizar(Prontuario prontuario) {
                notificado = true;
                recibido = prontuario;
            }
        }
        StubObserver observer = new StubObserver();
        NotificadorCEO.getInstance().agregarObserver(observer);

        Empleado empleado = new Empleado("Julieta", "julieta@excusassa.com", 2222);
        IExcusa excusa = new ExcusaInverosimil(
                "Me atrapó un desfile de culebras",
                "y no podía salir sin pisarlas",
                empleado
        );

        AdministradorProntuarios.getInstance().crearYPersistirProntuario(empleado, excusa);

        assertTrue(observer.notificado, "El observador debería haber sido notificado");
        assertNotNull(observer.recibido, "El observador debería haber recibido un prontuario");
        assertEquals("Julieta", observer.recibido.getEmpleado().getNombre(), "El empleado en el prontuario debe ser el correcto");
    }

}
