package com.excusassa.sistema_excusa.infraestructura.persistencia;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ExcusaRepository extends JpaRepository<Excusa, Long> {
    List<Excusa> findByEmpleadoNroLegajo(Integer nroLegajo);
}