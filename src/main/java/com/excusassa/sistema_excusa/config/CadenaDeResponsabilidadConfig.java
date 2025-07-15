package com.excusassa.sistema_excusa.config;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.CEO;
import com.excusassa.sistema_excusa.dominio.modelo.empleado.GerenteRRHH;
import com.excusassa.sistema_excusa.dominio.modelo.empleado.Recepcionista;
import com.excusassa.sistema_excusa.dominio.modelo.empleado.SupervisorArea;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.infraestructura.persistencia.ExcusaRepository;
import com.excusassa.sistema_excusa.servicios.encargado.CadenaEncargados;
import com.excusassa.sistema_excusa.servicios.encargado.CadenaManager;
import com.excusassa.sistema_excusa.servicios.encargado.IEncargado;
import com.excusassa.sistema_excusa.servicios.encargado.ManejadorDefecto;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;
import com.excusassa.sistema_excusa.servicios.notificacion.NotificadorCEO;
import com.excusassa.sistema_excusa.servicios.prontuario.ProntuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

@Configuration
public class CadenaDeResponsabilidadConfig {

    @Bean
    public CadenaManager cadenaManager(EmailSender emailSender, ProntuarioService prontuarioService, NotificadorCEO notificadorCEO) {
        List<IEncargado> encargadosIniciales = new LinkedList<>(List.of(
                new Recepcionista("Ana", "ana@email.com", 1001, ModoTrabajo.NORMAL, emailSender),
                new SupervisorArea("Luis", "luis@email.com", 1002, ModoTrabajo.NORMAL, emailSender),
                new GerenteRRHH("Carlos", "carlos@email.com", 1003, ModoTrabajo.NORMAL, emailSender),
                crearCeoObservable(emailSender, prontuarioService, notificadorCEO),
                new ManejadorDefecto()
        ));
        return new CadenaManager(encargadosIniciales);
    }

    private CEO crearCeoObservable(EmailSender emailSender, ProntuarioService prontuarioService, NotificadorCEO notificadorCEO) {
        CEO ceo = new CEO("Lucía (CEO)", "lucia@email.com", 1004, ModoTrabajo.NORMAL, emailSender, prontuarioService);
        notificadorCEO.agregarObserver(ceo);
        return ceo;
    }

    @Bean
    public CadenaEncargados cadenaEncargados(CadenaManager cadenaManager, ExcusaRepository excusaRepository) {
        return new CadenaEncargados(cadenaManager, excusaRepository);
    }
}