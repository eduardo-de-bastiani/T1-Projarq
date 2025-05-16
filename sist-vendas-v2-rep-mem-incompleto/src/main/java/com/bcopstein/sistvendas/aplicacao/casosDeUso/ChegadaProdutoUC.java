package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeEstoque;

@Component
public class ChegadaProdutoUC {
    private final ServicoDeEstoque servicoEstoque;

    @Autowired
    public ChegadaProdutoUC(ServicoDeEstoque servicoEstoque){
        this.servicoEstoque = servicoEstoque;
    }

    public void run(List<ItemPedidoDTO> produtos) {
        List<ItemPedidoModel> produtosModel = produtos.stream()
            .map(dto -> {
                ProdutoModel produto = servicoEstoque.produtoPorCodigo(dto.getProdutoId());
                return dto.toModel(produto);
            })
            .toList();

        servicoEstoque.chegadaDeProdutos(produtosModel);
    }
}
