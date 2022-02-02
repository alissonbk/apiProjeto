package com.api.negocio.service;

import com.api.negocio.dto.LoginDTO;
import com.api.negocio.model.Endereco;
import com.api.negocio.model.Usuario;
import com.api.negocio.exception.RegraNegocioException;
import com.api.negocio.repository.EnderecoRepository;
import com.api.negocio.repository.UsuarioRepository;
import com.api.negocio.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final EnderecoRepository enderecoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UsuarioService(UsuarioRepository repository, EnderecoRepository enderecoRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //LOGIN
    @NotNull
    @Transactional
    public Usuario login(@NotNull LoginDTO credentials) {
        return this.repository
            .findByEmailIgnoreCase(credentials.getEmail())
            .map(u -> {
                final var authToken = new UsernamePasswordAuthenticationToken(
                        u.getEmail(),
                        credentials.getSenha()
                );
                final var auth = this.authenticationManager.authenticate(authToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
                u.setAccessToken(this.jwtTokenProvider.createToken(u));
                u.setSenha(null);
                return u;
            })
            .orElseThrow( () -> new RegraNegocioException("Usuario ou senha inválidos"));
    }

    @Transactional
    public Usuario salvar(Usuario u){
        //Salva o endereco primeiro
        Endereco endereco = u.getEndereco();
        u.setEndereco(enderecoRepository.save(endereco));
        //Tipe de usuario é VENDEDOR por padrão
        u.setTipo(Usuario.Tipo.VENDEDOR);
        //Cryptografa a senha
        u.setSenha(passwordEncoder.encode(u.getSenha()));
        return this.repository.save(u);
    }

    public List<Usuario> listarTodos(){
        Usuario loggedUser = this.getLoggedUser();
        if(loggedUser.getTipo().toString() == "VENDEDOR"){
            return this.repository.findByEmail(loggedUser.getEmail());
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

    public void update(Long id, Usuario usuario) {
        //Salva o endereco primeiro
        Endereco endereco = usuario.getEndereco();
        usuario.setEndereco(enderecoRepository.save(endereco));
        //Tipo de usuario é VENDEDOR por padrão
        usuario.setTipo(Usuario.Tipo.VENDEDOR);
        this.repository.findById(id)
                .map(u -> {
                    usuario.setId(u.getId());
                    this.repository.save(usuario);
                    return u;
                }).orElseThrow(() -> new RegraNegocioException("Usuario não encontrado!"));
    }

    public Usuario getLoggedUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            Usuario usuario = this.repository.findUsuarioByEmail(username);
            return usuario;
        } else {
            String username = principal.toString();
            Usuario usuario = this.repository.findUsuarioByEmail(username);
            return usuario;
        }
    }


}
