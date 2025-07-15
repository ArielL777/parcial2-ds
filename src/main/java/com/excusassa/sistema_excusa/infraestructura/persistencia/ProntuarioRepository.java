package com.excusassa.sistema_excusa.infraestructura.persistencia;

import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
}