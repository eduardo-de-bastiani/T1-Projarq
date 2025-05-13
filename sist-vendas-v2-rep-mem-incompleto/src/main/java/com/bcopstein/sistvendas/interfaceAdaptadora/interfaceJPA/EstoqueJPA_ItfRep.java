package com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.ItemDeEstoque;
import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.Produto;

// public interface EstoqueJPA_ItfRep extends CrudRepository<ItemDeEstoque,Long>{
//     List<ItemDeEstoque> findAll();
//     Optional<ItemDeEstoque> findById(long id);
// }


public interface EstoqueJPA_ItfRep extends CrudRepository<ItemDeEstoque,Long>  {
    List<ItemDeEstoque> findAll();
    List<ItemDeEstoque> findByQuantidadeGreaterThan(int quantidade);
    int findQuantidadeById(long id);
    void baixaEstoque(long codProd, int qtdade);
    void adicionaEstoque(long id, int qtdade);
    List<ItemEstoque> estoqueCompleto();
    List<ItemEstoque> estoquePorProdutos(List<Long> idsProdutos);
}