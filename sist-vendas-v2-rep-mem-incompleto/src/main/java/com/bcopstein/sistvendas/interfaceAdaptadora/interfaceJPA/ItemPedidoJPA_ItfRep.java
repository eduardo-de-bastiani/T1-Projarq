package com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.ItemPedido;

public interface ItemPedidoJPA_ItfRep extends CrudRepository<ItemPedido, Long>{
    List<ItemPedido> findAll();
}
