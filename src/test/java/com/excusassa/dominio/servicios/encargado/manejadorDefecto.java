package com.excusassa.dominio.servicios.encargado;

import com.excusassa.dominio.modelo.empleado.Empleado;
import com.excusassa.dominio.modelo.excusa.ExcusaInverosimil;
import com.excusassa.dominio.modelo.excusa.IExcusa;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ManejadorDefectoTest {

    @Test
    void testMensajeDeRechazoSeImprimeCorrectamente() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        ManejadorDefecto manejador = new ManejadorDefecto();


        Empleado empleado = new Empleado("Tobías", "tobias@excusassa.com", 9999) {
            public String getNombre() { return "Tobías"; }
            public String getEmail() { return "tobias@excusassa.com"; }
            public int getNroLegajo() { return 9999; }
        };

        IExcusa excusa = new ExcusaInverosimil(
                "Me atacó un cisne en el parque",
                "Y no podía moverme por el susto",
                empleado
        );


        manejador.manejarExcusa(excusa);

        String salida = outContent.toString();

        assertTrue(salida.contains("Excusa rechazada: necesitamos pruebas contundentes."));
        assertTrue(salida.contains("Motivo: Me atacó un cisne en el parque"));
        assertTrue(salida.contains("Empleado: Tobías"));
    }
}

