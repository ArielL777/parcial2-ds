package com.excusassa.sistema_excusa.infraestructura.persistencia;

import com.excusassa.sistema_excusa.dominio.modelo.excusa.Excusa;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.EstadoExcusa;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.TipoExcusa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository

public interface ExcusaRepository extends JpaRepository<Excusa, Long> {
    List<Excusa> findByEmpleadoNroLegajo(Integer nroLegajo);

    @Query("SELECT e FROM Excusa e WHERE " +
            "(:tipo IS NULL OR e.tipo = :tipo) AND " +
            "(:encargado IS NULL OR e.nombreEncargadoQueProceso = :encargado) AND " +
            "(:fechaDesde IS NULL OR e.fecha >= :fechaDesde) AND " +
            "(:fechaHasta IS NULL OR e.fecha <= :fechaHasta)")
    List<Excusa> buscarConFiltros(
            @Param("motivo") TipoExcusa tipo,
            @Param("encargado") String encargado,
            @Param("fechaDesde") Date fechaDesde,
            @Param("fechaHasta") Date fechaHasta
    );
    @Modifying
    @Query("DELETE FROM Excusa e WHERE e.fecha < :fechaLimite")
    int deleteByFechaBefore(@Param("fechaLimite") Date fechaLimite);

    List<Excusa> findByEstado(EstadoExcusa estado);
    List<Excusa> findByEmpleadoNroLegajoAndFechaBetween(Integer nroLegajo, Date fechaDesde, Date fechaHasta);
}