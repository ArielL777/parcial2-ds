package com.excusassa.sistema_excusa.dominio.modelo.excusa;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.TipoExcusa;
import com.excusassa.sistema_excusa.dominio.modelo.excusa.enums.EstadoExcusa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "excusas")
@Data
@NoArgsConstructor
public class Excusa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoExcusa tipo;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @Enumerated(EnumType.STRING)
    private EstadoExcusa estado;

    private String nombreEncargadoQueProceso;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    public Excusa(TipoExcusa tipo, String descripcion, Empleado empleado) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.empleado = empleado;
        this.fecha = new Date();
        this.estado = EstadoExcusa.PENDIENTE;
    }
}