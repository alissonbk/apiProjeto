package com.api.negocio.controller;

import com.api.negocio.model.Cliente;
import com.api.negocio.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {

 private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Cliente> listarTodos(@RequestParam(value="nome", required = false) String nome){
        return service.listarTodos(nome);
    }

    @GetMapping("{id}")
    public Cliente listarId(@PathVariable Long id){
        return service.listarId(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente cadastrar(@RequestBody Cliente cliente){
        return service.salvar(cliente);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        service.excluir(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Cliente cliente){
        service.update(id, cliente);
    }


}
