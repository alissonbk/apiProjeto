package com.api.negocio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //Instant
    @NotNull
    @Column(nullable = false)
    private Instant dataCompra;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;
}
