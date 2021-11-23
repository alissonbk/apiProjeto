package com.crud.negocio.service;

import com.crud.negocio.exception.RegraNegocioException;
import com.crud.negocio.model.Cliente;
import com.crud.negocio.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) { this.repository = repository; }



    public Cliente salvar(Cliente c){
        return this.repository.save(c);
    }

    public List<Cliente> listarTodos(String nome){
        if(nome != null){
            return this.repository.findByNomeContains(nome);
        }else{
            return this.repository.findAll();
        }

    }

    public Cliente listarId(Long id){
        return this.repository.findById(id)
                .orElseThrow( () -> new RegraNegocioException("Cliente não encontrado!"));
    }

    public void excluir(Long id){
        this.repository.findById(id)
                .map(c -> {
                    this.repository.delete(c);
                    return c;
                }).orElseThrow( () -> new RegraNegocioException("Cliente não encontrado!"));
    }

    public void update(Long id, Cliente cliente){
        this.repository.findById(id)
                .map( c -> {
                    cliente.setId(c.getId());
                    this.repository.save(cliente);
                    return c;
                }).orElseThrow( () -> new RegraNegocioException("Cliente não encontrado!"));
    }
}
