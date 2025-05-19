package com.bcopstein.sistvendas.interfaceAdaptadora.implemRepositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IItemPedidoRepositorio;
import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.ItemPedido;
import com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA.ItemPedidoJPA_ItfRep;

@Repository
@Primary
public class ItemPedidoJPA implements IItemPedidoRepositorio {
    private ItemPedidoJPA_ItfRep itemPedidoRepository;

    @Autowired
    public ItemPedidoJPA(ItemPedidoJPA_ItfRep itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public void cadastraItemPedido(ItemPedidoModel item) {
        ItemPedido itemEntidade = ItemPedido.fromModel(item);
        itemPedidoRepository.save(itemEntidade);
    }
}
