package com.crud.negocio.service;

import com.crud.negocio.exception.RegraNegocioException;
import com.crud.negocio.model.Endereco;
import com.crud.negocio.model.Usuario;
import com.crud.negocio.repository.EnderecoRepository;
import com.crud.negocio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final EnderecoRepository enderecoRepository;

    public UsuarioService(UsuarioRepository repository, EnderecoRepository enderecoRepository) {
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
    }


    public Usuario salvar(Usuario u){
        //Salva o endereco primeiro
        Endereco endereco = u.getEndereco();
        u.setEndereco(enderecoRepository.save(endereco));
        //Tipe de usuario é VENDEDOR por padrão
        u.setTipo(Usuario.Tipo.VENDEDOR);
        return this.repository.save(u);
    }

    public List<Usuario> listarTodos(String nome){
        if(nome != null){
            return this.repository.findByNomeContains(nome);
        }else{
            return this.repository.findAll();
        }

    }

    public Usuario listarId(Long id){
        return this.repository.findById(id)
                .orElseThrow( () -> new RegraNegocioException("Usuario não encontrado!"));
    }

    public void excluir(Long id){
        this.repository.findById(id)
                .map(u -> {
                    this.repository.delete(u);
                    return u;
                }).orElseThrow( () -> new RegraNegocioException("Usuario não encontrado!"));
    }

    public void update(Long id, Usuario usuario){
        //Salva o endereco primeiro
        Endereco endereco = usuario.getEndereco();
        usuario.setEndereco(enderecoRepository.save(endereco));
        //Tipe de usuario é VENDEDOR por padrão
        usuario.setTipo(Usuario.Tipo.VENDEDOR);
        this.repository.findById(id)
                .map( u -> {
                    usuario.setId(u.getId());
                    this.repository.save(usuario);
                    return u;
                }).orElseThrow( () -> new RegraNegocioException("Usuario não encontrado!"));
    }
}
