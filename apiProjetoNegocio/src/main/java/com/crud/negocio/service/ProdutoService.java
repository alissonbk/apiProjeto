package com.crud.negocio.service;

import com.crud.negocio.exception.RegraNegocioException;
import com.crud.negocio.model.Produto;
import com.crud.negocio.repository.ProdutoRepository;
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
