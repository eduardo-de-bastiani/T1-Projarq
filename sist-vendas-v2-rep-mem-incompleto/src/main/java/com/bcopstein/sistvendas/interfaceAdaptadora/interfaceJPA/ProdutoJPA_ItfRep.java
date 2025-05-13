package com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.Produto;

public interface ProdutoJPA_ItfRep extends CrudRepository<Produto, Long>{
    List<Produto> findAll();
    Produto findById(long id);
}
