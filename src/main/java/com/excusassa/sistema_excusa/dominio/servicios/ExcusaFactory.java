package com.excusassa.sistema_excusa.dominio.servicios;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.*;
import org.springframework.stereotype.Component;

@Component
public class ExcusaFactory {

    public IExcusa crear(String motivo, String descripcion, Empleado empleado) {
        return switch (motivo.toLowerCase()) {
            case "me quedé dormido", "perdí el colectivo" -> new ExcusaTrivial(motivo, descripcion, empleado);
            case "se cortó la luz" -> new ExcusaCorteLuz(motivo, descripcion, empleado);
            case "cuidar a un familiar" -> new ExcusaCuidadoFamiliar(motivo, descripcion, empleado);
            case "fui abducido por aliens", "una paloma robó mi bicicleta" -> new ExcusaCompleja(motivo, descripcion, empleado);
            default -> new ExcusaInverosimil(motivo, descripcion, empleado);
        };
    }
}