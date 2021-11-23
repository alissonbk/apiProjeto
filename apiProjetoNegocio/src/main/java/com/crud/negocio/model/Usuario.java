package com.crud.negocio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //Email e senha usados para login
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;
    //

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Endereco endereco;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    public enum Tipo{
        ADMIN,
        VENDEDOR
    }
}
