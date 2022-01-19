package com.api.negocio.service;

import com.api.negocio.model.Endereco;
import com.api.negocio.repository.ClienteRepository;
import com.api.negocio.repository.EnderecoRepository;
import com.api.negocio.exception.RegraNegocioException;
import com.api.negocio.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClienteService {
    private final ClienteRepository repository;
    private final EnderecoRepository enderecoRepository;

    public ClienteService(ClienteRepository repository, EnderecoRepository enderecoRepository) {
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
    }



    public Cliente salvar(Cliente c){
        Endereco endereco = c.getEndereco();
        enderecoRepository.save(endereco);
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
        //Salva o endereco primeiro
        Endereco endereco = cliente.getEndereco();
        cliente.setEndereco(enderecoRepository.save(endereco));
        this.repository.findById(id)
                .map( c -> {
                    cliente.setId(c.getId());
                    this.repository.save(cliente);
                    return c;
                }).orElseThrow( () -> new RegraNegocioException("Cliente não encontrado!"));
    }
}
