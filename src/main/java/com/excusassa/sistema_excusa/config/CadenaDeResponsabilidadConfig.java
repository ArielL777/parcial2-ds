package com.excusassa.sistema_excusa.config;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.*;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.dominio.servicios.encargado.CadenaEncargados;
import com.excusassa.sistema_excusa.dominio.servicios.encargado.IEncargado;
import com.excusassa.sistema_excusa.dominio.servicios.encargado.ManejadorDefecto;
import com.excusassa.sistema_excusa.dominio.servicios.modotrabajo.IModoTrabajo;
import com.excusassa.sistema_excusa.dominio.servicios.modotrabajo.ModoNormal;
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
    public IEncargado ceo(IModoTrabajo modoNormal, EmailSender emailSender, IEncargado manejadorDefecto) {
        CEO ceo = new CEO("Lucía (CEO)", "lucia@excusassa.com", 1004, modoNormal, emailSender);
        ceo.setSiguiente(manejadorDefecto); // Lo enlazamos con el siguiente
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
    @Qualifier("primerEncargado") // Le ponemos una etiqueta para identificarlo
    public IEncargado recepcionista(IModoTrabajo modoNormal, EmailSender emailSender, IEncargado supervisorArea) {
        Recepcionista recepcionista = new Recepcionista("Ana (Recepcionista)", "ana@excusassa.com", 1001, modoNormal, emailSender);
        recepcionista.setSiguiente(supervisorArea);
        return recepcionista;
    }

    @Bean
    public CadenaEncargados cadenaEncargados(@Qualifier("primerEncargado") IEncargado primerEncargado) {
        return new CadenaEncargados(primerEncargado);
    }
}