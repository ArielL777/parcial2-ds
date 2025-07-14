package com.excusassa.sistema_excusa.servicios.prontuario;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.IExcusa;
import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import com.excusassa.sistema_excusa.infraestructura.persistencia.ProntuarioRepository;
import com.excusassa.sistema_excusa.servicios.notificacion.NotificadorCEO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final NotificadorCEO notificadorCEO;

    public ProntuarioService(ProntuarioRepository prontuarioRepository, NotificadorCEO notificadorCEO) {
        this.prontuarioRepository = prontuarioRepository;
        this.notificadorCEO = notificadorCEO;
    }

    public void crearYPersistirProntuario(Empleado empleado, IExcusa excusa) {
        Prontuario prontuario = new Prontuario(
                empleado,
                excusa,
                empleado.getNroLegajo(),
                new Date()
        );

        prontuarioRepository.save(prontuario);
        notificadorCEO.notificarObservers(prontuario);
    }

    public List<Prontuario> obtenerTodos() {
        return prontuarioRepository.findAll();
    }
}