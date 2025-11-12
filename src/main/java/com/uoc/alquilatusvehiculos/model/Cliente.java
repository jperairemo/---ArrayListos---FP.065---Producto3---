package com.uoc.alquilatusvehiculos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private String dni;
    private String telefono;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
