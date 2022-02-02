package com.api.negocio.controller;

import com.api.negocio.dto.LoginDTO;
import com.api.negocio.exception.ApiErrors;
import com.api.negocio.model.Usuario;
import com.api.negocio.service.UsuarioService;
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
        loginDTO.setSenha(null);
        return this.usuarioService.login(loginDTO);
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleBadCredentials(BadCredentialsException ex) {
        return new ApiErrors(ex.getMessage());
    }


}
