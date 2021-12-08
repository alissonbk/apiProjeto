package com.crud.negocio.controller;

import com.crud.negocio.model.Usuario;
import com.crud.negocio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    public UsuarioController(UsuarioService service) { this.service = service;
    }


    @GetMapping()
    public List<Usuario> listarTodos(@RequestParam(value="nome", required = false) String nome){
        return service.listarTodos(nome);
    }

    @GetMapping("{id}")
    public Usuario listarId(@PathVariable Long id){
        return service.listarId(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrar(@RequestBody Usuario usuario){
        return service.salvar(usuario);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        service.excluir(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Usuario usuario){
        usuario.setTipo(Usuario.Tipo.VENDEDOR);
        service.update(id, usuario);
    }



}
