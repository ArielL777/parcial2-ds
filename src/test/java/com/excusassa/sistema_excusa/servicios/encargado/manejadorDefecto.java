package com.excusassa.sistema_excusa.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.TipoExcusa;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ManejadorDefectoTest {

    @Test
    void testMensajeDeRechazoSeImprimeCorrectamente() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ManejadorDefecto manejador = new ManejadorDefecto();

        Empleado empleado = new Empleado("Tobías", "tobias@excusassa.com", 9999);

        Excusa excusa = new Excusa(
                TipoExcusa.INVEROSIMIL,
                "No podía moverme por el susto",
                empleado
        );

        manejador.manejarExcusa(excusa);

        String salida = outContent.toString();

        assertTrue(salida.contains("Excusa rechazada: necesitamos pruebas contundentes."));
        assertTrue(salida.contains("Motivo: " + TipoExcusa.INVEROSIMIL.getMotivo()));
        assertTrue(salida.contains("Empleado: Tobías"));

        System.setOut(System.out);
    }
}