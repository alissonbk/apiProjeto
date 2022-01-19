package com.api.negocio.dto;


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
    private Long id;
    private BigDecimal valor;
    private String descricao;
    private String marca;
}
