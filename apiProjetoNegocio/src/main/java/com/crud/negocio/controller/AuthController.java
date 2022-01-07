package com.crud.negocio.controller;

import com.crud.negocio.dto.LoginDTO;
import com.crud.negocio.exception.ApiErrors;
import com.crud.negocio.model.Usuario;
import com.crud.negocio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/login")
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @PostMapping
    Usuario login(@RequestBody LoginDTO loginDTO){
       return this.usuarioService.login(loginDTO);
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleBadCredentials(BadCredentialsException ex) {
        return new ApiErrors(ex.getMessage());
    }


}
