package com.crud.negocio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Endereco endereco;

    //Dados para login
    @Column(nullable = false)
    @NotEmpty(message = "Email é obrigatório!")
    private String email;

    @NotEmpty(message = "Senha é obrigatório!")
    private String senha;

    @Column(nullable = false)
    private boolean ativo = true;

    @Transient
    @JsonProperty("access_token")
    private String accessToken;



    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    public enum Tipo{
        ADMIN,
        VENDEDOR
    }
}
