package com.crud.negocio.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoProdutoDTO {
    private Long codigo;
    private BigDecimal valor;
    private String descricao;
}
