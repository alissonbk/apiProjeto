package com.crud.negocio.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class LoginDTO {
    @Email
    private String email;
    private String senha;
}
