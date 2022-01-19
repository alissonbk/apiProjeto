package com.api.negocio.controller;


import com.api.negocio.dto.CompraDTO;
import com.api.negocio.model.Compra;
import com.api.negocio.service.CompraService;
import com.api.negocio.dto.InfoCompraDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/compras")
public class CompraController {
    private final CompraService service;
    public CompraController(CompraService service) { this.service = service; }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Long salvar(@RequestBody CompraDTO dto){
        Compra compra = service.cadastrar(dto);
        return compra.getId();
    }

    @GetMapping("{id}")
    public InfoCompraDTO listarById(@PathVariable Long id){
        return service.obterCompra(id);
    }

    @GetMapping()
    public Page<InfoCompraDTO> listarTodos(Pageable pageable, @RequestParam(value="cliente", required = false) Long c,
                                           @RequestParam(value="vendedor", required = false) Long v,
                                           @RequestParam(value="produto", required = false) Long p){
        return service.obterTodasCompras(pageable, c, v, p);
    }

    @DeleteMapping("{id}")
    public void excluirById(@PathVariable Long id){
        service.excluirCompra(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@PathVariable Long id, @RequestBody CompraDTO compra){
        service.updateCompra(id, compra);
    }
}
