package com.api.negocio.security;

import com.api.negocio.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UsuarioRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(UsuarioRepository repository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = repository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtTokenProvider, userRepository);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                email ->
                        this.userRepository.findByEmailIgnoreCase(email)
                                .map(UserPrincipal::new)
                                .orElseThrow(
                                        () -> new UsernameNotFoundException(
                                                "Usuário " + email + " não encontrado"
                                        )
                                )
        ).passwordEncoder(this.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable() // csrf não é necessário(sem cookies e sessão)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // Filtro JWT que faz a autenticação de usuários
                // com base no token enviado na requisição
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)

                // Rotas e configurações de acesso
                .authorizeRequests()
                    .antMatchers("/v1/login")
                        .permitAll()
                    .antMatchers(HttpMethod.GET, "/v1/usuarios/**")
                        .authenticated()
                    .antMatchers("/v1/usuarios/**")
                        .hasAuthority("ADMIN")

                    .anyRequest().authenticated();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Bean
    public CorsFilter corsFilter() {
        final var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
