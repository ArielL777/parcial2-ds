package com.excusassa.sistema_excusa.dominio.modelo.empleado;

import com.excusassa.sistema_excusa.dominio.modelo.prontuario.Prontuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
public class Empleado {

    public Empleado(String nombre, String email, int nroLegajo) {
        this.nombre = nombre;
        this.email = email;
        this.nroLegajo = nroLegajo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private int nroLegajo;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Prontuario> prontuarios;
}