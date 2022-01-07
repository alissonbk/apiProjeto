package com.crud.negocio.controller;

import com.crud.negocio.model.Usuario;
import com.crud.negocio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    public UsuarioController(UsuarioService service) { this.service = service;
    }

    @CrossOrigin(origins = "*")
    @GetMapping()
    public List<Usuario> listarTodos(@RequestParam(value="nome", required = false) String nome){
        return service.listarTodos(nome);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("{id}")
    public Usuario listarId(@PathVariable Long id){
        return service.listarId(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrar(@RequestBody @Valid Usuario usuario){
        return service.salvar(usuario);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        service.excluir(id);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Usuario usuario){
        usuario.setTipo(Usuario.Tipo.VENDEDOR);
        service.update(id, usuario);
    }



}
