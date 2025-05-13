package com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.ItemDeEstoque;

public interface EstoqueJPA_ItfRep extends CrudRepository<ItemDeEstoque,Long>  {
    List<ItemDeEstoque> findAll();

    List<ItemDeEstoque> findByQuantidadeGreaterThan(int quantidade);

    int findQuantidadeById(long id);
    
    List<ItemDeEstoque> findByProdutoIdIn(List<Long> idsProdutos);
}