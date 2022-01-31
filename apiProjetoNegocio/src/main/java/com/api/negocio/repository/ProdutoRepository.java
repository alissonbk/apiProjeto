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
    static Specification<Produto> filtroProdutos(String descricao, String marca, BigDecimal valor) {
        return (produto, cq, cb) -> {
            final var predicates = new ArrayList<Predicate>();
            if(descricao != null){
                predicates.add(cb.like(cb.upper(produto.get("descricao")), descricao.toUpperCase() + "%"));
            }
            if(marca != null){
                predicates.add(cb.like(cb.upper(produto.get("marca")), marca.toUpperCase() + "%"));
            }
            if(valor != null){
                predicates.add(cb.equal(produto.get("valor"), valor));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
