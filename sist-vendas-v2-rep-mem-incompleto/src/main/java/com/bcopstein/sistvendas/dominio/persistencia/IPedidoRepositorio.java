package com.bcopstein.sistvendas.dominio.persistencia;

import com.bcopstein.sistvendas.dominio.modelos.PedidoModel;

public interface IPedidoRepositorio {
    void cadastraPedido(PedidoModel pedido);
}
