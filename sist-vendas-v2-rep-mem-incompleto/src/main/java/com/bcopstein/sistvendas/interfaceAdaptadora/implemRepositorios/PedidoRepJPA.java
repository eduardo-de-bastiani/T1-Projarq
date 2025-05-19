package com.bcopstein.sistvendas.interfaceAdaptadora.implemRepositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.sistvendas.dominio.modelos.PedidoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IPedidoRepositorio;
import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.Pedido;
import com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA.PedidoJPA_ItfRep;

@Repository
@Primary
public class PedidoRepJPA implements IPedidoRepositorio {
    private PedidoJPA_ItfRep pedidoRepository;

    @Autowired
    public PedidoRepJPA(PedidoJPA_ItfRep pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void cadastraPedido(PedidoModel pedido) {
        Pedido entidade = Pedido.fromModel(pedido);
        pedidoRepository.save(entidade);
    }
}
