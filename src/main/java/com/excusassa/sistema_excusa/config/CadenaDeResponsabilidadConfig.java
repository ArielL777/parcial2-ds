package com.excusassa.sistema_excusa.config;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.*;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;
import com.excusassa.sistema_excusa.infraestructura.persistencia.ExcusaRepository;
import com.excusassa.sistema_excusa.servicios.encargado.CadenaEncargados;
import com.excusassa.sistema_excusa.servicios.encargado.IEncargado;
import com.excusassa.sistema_excusa.servicios.encargado.ManejadorDefecto;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;
import com.excusassa.sistema_excusa.servicios.prontuario.ProntuarioService;
import com.excusassa.sistema_excusa.servicios.notificacion.NotificadorCEO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CadenaDeResponsabilidadConfig {

    @Bean
    public IEncargado manejadorDefecto() {
        return new ManejadorDefecto();
    }

    @Bean
    public IEncargado ceo(EmailSender emailSender, IEncargado manejadorDefecto, ProntuarioService prontuarioService, NotificadorCEO notificadorCEO) {
        CEO ceo = new CEO("Lucía (CEO)", "lucia@excusassa.com", 1004, ModoTrabajo.NORMAL, emailSender, prontuarioService);
        notificadorCEO.agregarObserver(ceo);
        ceo.setSiguiente(manejadorDefecto);
        return ceo;
    }

    @Bean
    public IEncargado gerenteRRHH(EmailSender emailSender, IEncargado ceo) {
        GerenteRRHH gerente = new GerenteRRHH("Carlos (Gerente)", "carlos@excusassa.com", 1003, ModoTrabajo.NORMAL, emailSender);
        gerente.setSiguiente(ceo);
        return gerente;
    }

    @Bean
    public IEncargado supervisorArea(EmailSender emailSender, IEncargado gerenteRRHH) {
        SupervisorArea supervisor = new SupervisorArea("Luis (Supervisor)", "luis@excusassa.com", 1002, ModoTrabajo.NORMAL, emailSender);
        supervisor.setSiguiente(gerenteRRHH);
        return supervisor;
    }

    @Bean
    @Qualifier("primerEncargado")
    public IEncargado recepcionista(EmailSender emailSender, IEncargado supervisorArea) {
        Recepcionista recepcionista = new Recepcionista("Ana (Recepcionista)", "ana@excusassa.com", 1001, ModoTrabajo.NORMAL, emailSender);
        recepcionista.setSiguiente(supervisorArea);
        return recepcionista;
    }

    @Bean
    public CadenaEncargados cadenaEncargados(@Qualifier("primerEncargado") IEncargado primerEncargado, ExcusaRepository excusaRepository) {
        return new CadenaEncargados(primerEncargado, excusaRepository);
    }
}