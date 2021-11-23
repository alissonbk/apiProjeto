package com.crud.negocio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoCompraDTO {
    private Long codigo;
    private String dataCompra;


    private InfoClienteDTO cliente;
    private InfoVendedorDTO vendedor;
    private InfoProdutoDTO produto;


}
