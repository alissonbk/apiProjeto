package com.api.negocio.repository;

import com.api.negocio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNomeContains(String nome);
    Optional<Usuario> findByEmailIgnoreCase(@NotNull String email);
    List<Usuario> findByEmail(String email);
    Usuario findUsuarioByEmail(String email);
}
