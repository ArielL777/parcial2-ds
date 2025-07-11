package com.excusassa.dominio.servicios.modotrabajo;

import com.excusassa.dominio.modelo.empleado.Empleado;
import com.excusassa.dominio.modelo.excusa.ExcusaInverosimil;
import com.excusassa.dominio.modelo.excusa.IExcusa;
import com.excusassa.dominio.servicios.encargado.EncargadoAbstracto;
import com.excusassa.dominio.servicios.encargado.IEncargado;
import com.excusassa.infraestructura.email.EmailSender;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ModoProductivoTest {

    @Test
    void testModoProductivoDelegacionYEnvioEmail() {

        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Empleado empleado = new Empleado("Sofía", "sofia@excusassa.com", 4444) {
            public String getNombre() { return "Sofía"; }
            public String getEmail() { return "sofia@excusassa.com"; }
            public int getNroLegajo() { return 4444; }
        };

        IExcusa excusa = new ExcusaInverosimil(
                "Un delfín me robó la mochila",
                "y tuve que recuperarla en el mar",
                empleado
        );

        IEncargado siguiente = new IEncargado() {
            public void manejarExcusa(IExcusa e) {
                System.out.println("Excusa delegada al siguiente encargado.");
            }
            public void setSiguiente(IEncargado s) {}
        };

        EncargadoAbstracto encargado = new EncargadoAbstracto(
                "Pedro", "pedro@excusassa.com", 1234,
                new ModoProductivo(),
                new EmailSender() {
                    @Override
                    public void enviarEmail(String destino, String origen, String asunto, String cuerpo) {
                        System.out.println("Email enviado a " + destino);
                    }
                }
        ) {
            @Override public boolean puedeProcesar(IExcusa excusa) { return false; }
            @Override public void procesarExcusaInterna(IExcusa excusa) {}
        };

        encargado.setSiguiente(siguiente);

        encargado.manejarExcusa(excusa);

        String salida = out.toString();

        assertTrue(salida.contains("Email enviado a cto@excusassa.com"));
        assertTrue(salida.contains("Excusa delegada al siguiente encargado."));

        System.setOut(originalOut);
        System.out.println("Test de ModoProductivo ejecutado correctamente.");
    }

}
