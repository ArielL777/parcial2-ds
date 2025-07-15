package com.excusassa.sistema_excusa.dominio.modelo.prontuario;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.Empleado;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "prontuarios")
@Data
@NoArgsConstructor
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @Column(nullable = false)
    private String motivoExcusa;

    @Column(nullable = false)
    private int nroLegajo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    public Prontuario(Empleado empleado, String motivoExcusa, int nroLegajo, Date fecha) {
        this.empleado = empleado;
        this.motivoExcusa = motivoExcusa;
        this.nroLegajo = nroLegajo;
        this.fecha = fecha;
    }
}