package com.api.negocio.service;

import com.api.negocio.repository.ProdutoRepository;
import com.api.negocio.exception.RegraNegocioException;
import com.api.negocio.model.Produto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    public ProdutoService(ProdutoRepository repository) { this.repository = repository; }


    public Produto salvar(Produto p){
        return this.repository.save(p);
    }

    public List<Produto> listarTodos(String desc){

        if(desc != null){
            return this.repository.findByDescricaoContains(desc);
        }else{
            return this.repository.findAll();
        }

    }


    public Produto listarId(Long id){
        return this.repository.findById(id)
                .orElseThrow( () -> new RegraNegocioException("Produto não encontrado!"));
    }

    public void excluir(Long id){
        this.repository.findById(id)
                .map(p -> {
                    this.repository.delete(p);
                    return p;
                }).orElseThrow( () -> new RegraNegocioException("Produto não encontrado!"));
    }

    public void update(Long id, Produto produto){
        this.repository.findById(id)
                .map( p -> {
                    produto.setId(p.getId());
                    this.repository.save(produto);
                    return p;
                }).orElseThrow( () -> new RegraNegocioException("Produto não encontrado!"));
    }

}
