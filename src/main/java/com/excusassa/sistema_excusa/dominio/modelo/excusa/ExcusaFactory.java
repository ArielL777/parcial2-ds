package com.excusassa.sistema_excusa.dominio.modelo.excusa;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.TipoExcusa;
import org.springframework.stereotype.Component;

@Component
public class ExcusaFactory {

    public Excusa crear(String motivo, String descripcion, Empleado empleado) {
        TipoExcusa tipo = TipoExcusa.fromString(motivo);
        return new Excusa(tipo, descripcion, empleado);
    }
}