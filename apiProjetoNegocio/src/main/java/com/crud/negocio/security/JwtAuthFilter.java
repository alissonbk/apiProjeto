package com.crud.negocio.security;

import com.crud.negocio.repository.UsuarioRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository userRepository;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider, UsuarioRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        if (!req.getRequestURI().contains("login")) {
            Optional.ofNullable(req.getHeader(HttpHeaders.AUTHORIZATION))
                    .map(String::trim)
                    .filter(header -> header.startsWith("Bearer "))
                    .map(h -> h.replaceFirst("Bearer ", ""))
                    .map(this.jwtTokenProvider::parseToken)
                    .filter(claims -> claims.getExpiration().after(new Date())) // filtrar token com data expirada
                    .ifPresent(token -> {
                        final var u = this.userRepository.findByEmailIgnoreCase(token.getSubject())
                                .orElseThrow(() -> new IllegalStateException("token criado com usuário inválido"));
                        final var userPrincipal = new UserPrincipal(u);
                        final var auth = new UsernamePasswordAuthenticationToken(
                                userPrincipal,
                                userPrincipal.getPassword(),
                                userPrincipal.getAuthorities()
                        );
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    });
        }
        filterChain.doFilter(req, resp);
    }
}
