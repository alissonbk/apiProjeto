package com.crud.negocio.repository;

import com.crud.negocio.model.Compra;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;



@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>, JpaSpecificationExecutor<Compra> {

    static Specification<Compra> filtroVendedorClienteCompra(Long clienteId, Long vendedorId, Long produtoId) {
        return (compra, cq, cb) -> {
            final var predicates = new ArrayList<Predicate>();

            if (clienteId != null) {
                predicates.add(cb.equal(compra.get("cliente"), clienteId));
            }
            if (vendedorId != null) {
                predicates.add(cb.equal(compra.get("vendedor"), vendedorId));
            }
            if(produtoId != null){
                predicates.add(cb.equal(compra.get("produto"), produtoId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
