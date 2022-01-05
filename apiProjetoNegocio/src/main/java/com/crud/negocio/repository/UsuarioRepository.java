package com.crud.negocio.repository;

import com.crud.negocio.model.Cliente;
import com.crud.negocio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNomeContains(String nome);
    Optional<Usuario> findByEmailIgnoreCase(@NotNull String email);
}
