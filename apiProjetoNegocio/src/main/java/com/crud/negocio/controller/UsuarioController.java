package com.crud.negocio.controller;

import com.crud.negocio.model.Endereco;
import com.crud.negocio.model.Usuario;
import com.crud.negocio.repository.EnderecoRepository;
import com.crud.negocio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final EnderecoRepository enderecoRepository;
    public UsuarioController(UsuarioService service, EnderecoRepository enderecoRepository) { this.service = service;
        this.enderecoRepository = enderecoRepository;
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
        //salva o endereco primeiro
        Endereco endereco = usuario.getEndereco();
        usuario.setEndereco(enderecoRepository.save(endereco));
        //tipo de usuario é VENDEDOR por padrão
        usuario.setTipo(Usuario.Tipo.VENDEDOR);
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
