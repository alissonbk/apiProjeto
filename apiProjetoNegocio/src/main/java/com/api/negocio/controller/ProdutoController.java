package com.api.negocio.controller;


import com.api.negocio.model.Produto;
import com.api.negocio.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/produtos")
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
