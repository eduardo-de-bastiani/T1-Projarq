package com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.Pedido;

public interface PedidoJPA_ItfRep extends CrudRepository<Pedido, Long>{
    List<Pedido> findAll();
}
