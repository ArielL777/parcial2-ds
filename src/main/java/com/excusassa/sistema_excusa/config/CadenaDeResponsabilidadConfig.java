package com.excusassa.sistema_excusa.config;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.*;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.infraestructura.persistencia.ExcusaRepository;
import com.excusassa.sistema_excusa.servicios.encargado.CadenaEncargados;
import com.excusassa.sistema_excusa.servicios.encargado.IEncargado;
import com.excusassa.sistema_excusa.servicios.encargado.ManejadorDefecto;
import com.excusassa.sistema_excusa.servicios.modotrabajo.IModoTrabajo;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoNormal;
import com.excusassa.sistema_excusa.servicios.prontuario.ProntuarioService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CadenaDeResponsabilidadConfig {

    @Bean
    public IModoTrabajo modoNormal() {
        return new ModoNormal();
    }

    @Bean
    public IEncargado manejadorDefecto() {
        return new ManejadorDefecto();
    }

    @Bean
    public IEncargado ceo(IModoTrabajo modoNormal, EmailSender emailSender,
                          IEncargado manejadorDefecto, ProntuarioService prontuarioService) {

        CEO ceo = new CEO("Lucía (CEO)", "lucia@excusassa.com", 1004, modoNormal, emailSender, prontuarioService);

        ceo.setSiguiente(manejadorDefecto);
        return ceo;
    }

    @Bean
    public IEncargado gerenteRRHH(IModoTrabajo modoNormal, EmailSender emailSender, IEncargado ceo) {
        GerenteRRHH gerente = new GerenteRRHH("Carlos (Gerente)", "carlos@excusassa.com", 1003, modoNormal, emailSender);
        gerente.setSiguiente(ceo);
        return gerente;
    }

    @Bean
    public IEncargado supervisorArea(IModoTrabajo modoNormal, EmailSender emailSender, IEncargado gerenteRRHH) {
        SupervisorArea supervisor = new SupervisorArea("Luis (Supervisor)", "luis@excusassa.com", 1002, modoNormal, emailSender);
        supervisor.setSiguiente(gerenteRRHH);
        return supervisor;
    }

    @Bean
    @Qualifier("primerEncargado")
    public IEncargado recepcionista(IModoTrabajo modoNormal, EmailSender emailSender, IEncargado supervisorArea) {
        Recepcionista recepcionista = new Recepcionista("Ana (Recepcionista)", "ana@excusassa.com", 1001, modoNormal, emailSender);
        recepcionista.setSiguiente(supervisorArea);
        return recepcionista;
    }

    @Bean
    public CadenaEncargados cadenaEncargados(@Qualifier("primerEncargado") IEncargado primerEncargado, ExcusaRepository excusaRepository) {
        return new CadenaEncargados(primerEncargado, excusaRepository);
    }
}