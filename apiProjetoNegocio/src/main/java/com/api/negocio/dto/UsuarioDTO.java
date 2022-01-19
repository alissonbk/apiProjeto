package com.api.negocio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    Long id;
    String email;
    String senha;
    String nome;
    String cpf;
    Long endereco;

}
