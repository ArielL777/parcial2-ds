package com.excusassa.sistema_excusa.infraestructura.persistencia;

import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class ProntuarioRepository {

    private final List<Prontuario> prontuarios = new CopyOnWriteArrayList<>();

    public void save(Prontuario prontuario) {
        prontuarios.add(prontuario);
    }

    public List<Prontuario> findAll() {
        return new ArrayList<>(prontuarios);
    }

    public List<Prontuario> findById(Integer legajo) {
        return new ArrayList<>(prontuarios);
    }
}