package com.crud.negocio.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraDTO {
    private Long cliente;
    private Long vendedor;
    private Long produto;

}
