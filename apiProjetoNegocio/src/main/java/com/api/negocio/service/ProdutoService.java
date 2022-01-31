package com.api.negocio.service;

import com.api.negocio.repository.ProdutoRepository;
import com.api.negocio.exception.RegraNegocioException;
import com.api.negocio.model.Produto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.api.negocio.repository.ProdutoRepository.filtroProdutos;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    public ProdutoService(ProdutoRepository repository) { this.repository = repository; }


    public Produto salvar(Produto p){
        return this.repository.save(p);
    }

    public List<Produto> listarTodos(String descricao, String marca, BigDecimal valor){

        if(descricao == null && marca == null && valor == null){
            return this.repository.findAll();
        }else{
            return this.repository.findAll(filtroProdutos(descricao, marca, valor));
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
