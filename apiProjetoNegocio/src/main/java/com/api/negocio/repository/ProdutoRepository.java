package com.api.negocio.repository;

import com.api.negocio.model.Produto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    // Filtro dinamico
    static Specification<Produto> filtroProdutos(String descricao, String marca) {
        return (produto, cq, cb) -> {
            final var predicates = new ArrayList<Predicate>();
            if(descricao != ""){
                predicates.add(cb.like(cb.upper(produto.get("descricao")), descricao.toUpperCase() + "%"));
            }
            if(marca != ""){
                predicates.add(cb.like(cb.upper(produto.get("marca")), marca.toUpperCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
