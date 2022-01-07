package com.crud.negocio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoVendedorDTO {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private Long endereco;
}
