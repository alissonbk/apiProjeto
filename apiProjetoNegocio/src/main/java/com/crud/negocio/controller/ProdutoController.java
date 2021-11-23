package com.crud.negocio.controller;


import com.crud.negocio.model.Cliente;
import com.crud.negocio.model.Produto;
import com.crud.negocio.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService service;
    public ProdutoController(ProdutoService service) { this.service = service; }


    @GetMapping()
    public List<Produto> listarTodos(@RequestParam(value="descricao", required = false) String desc){
        return service.listarTodos(desc);
    }

    @GetMapping("{id}")
    public Produto listarId(@PathVariable Long id){
        return service.listarId(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Produto cadastrar(@RequestBody Produto produto){
        return service.salvar(produto);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        service.excluir(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Produto produto){
        service.update(id, produto);
    }

}
