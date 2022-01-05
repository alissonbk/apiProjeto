package com.crud.negocio.service;

import com.crud.negocio.dto.LoginDTO;
import com.crud.negocio.exception.RegraNegocioException;
import com.crud.negocio.model.Endereco;
import com.crud.negocio.model.Usuario;
import com.crud.negocio.repository.EnderecoRepository;
import com.crud.negocio.repository.UsuarioRepository;
import com.crud.negocio.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {
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

    //DetailsConfigs
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmailIgnoreCase(email).orElseThrow( () -> new UsernameNotFoundException("Usuario não encontrado!"));
        return User
                .builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getTipo().name())
                .build();
    }
    public UserDetails autenticar(Usuario usuario){
        UserDetails userDetails = loadUserByUsername(usuario.getEmail());
        if(passwordEncoder.matches(usuario.getSenha(), userDetails.getPassword()) ){
            return userDetails;
        }
        throw new RegraNegocioException("Senha invalida!");
    }

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

                    return u;
                })
                .orElseThrow( () -> new RegraNegocioException("Usuario ou senha inválidos"));
    }




    //Basic Services
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
