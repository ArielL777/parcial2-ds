package com.excusassa.infraestructura.persistencia;

import com.excusassa.dominio.modelo.empleado.Empleado;
import com.excusassa.dominio.modelo.excusa.IExcusa;
import com.excusassa.dominio.modelo.prontuario.Prontuario;
import com.excusassa.dominio.servicios.notificacion.NotificadorCEO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdministradorProntuarios {

    private static AdministradorProntuarios instancia;
    private final List<Prontuario> prontuarios;


    private AdministradorProntuarios() {
        prontuarios = new ArrayList<>();
    }

    public static AdministradorProntuarios getInstance() {
        if (instancia == null) {
            instancia = new AdministradorProntuarios();
        }
        return instancia;
    }

    public void crearYPersistirProntuario(Empleado empleado, IExcusa excusa) {
        Prontuario prontuario = new Prontuario(
                empleado,
                excusa,
                empleado.getNroLegajo(),
                new Date()
        );
        this.persistirProntuario(prontuario);
    }

    private void persistirProntuario(Prontuario prontuario) {
        this.prontuarios.add(prontuario);
        NotificadorCEO.getInstance().notificarObservers(prontuario);
    }

    public List<Prontuario> getProntuarios() {
        return prontuarios;
    }

    public static void resetInstance() {
        instancia = null;
    }
}