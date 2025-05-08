package com.bcopstein.sistvendas.dominio.servicos.StrategyImposto;

import java.util.List;

import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;

public class ImpostoPB implements ICalculoImposto {

    @Override
    public double calculoimposto(OrcamentoModel orcamento) {
        List<ItemPedidoModel> itens = orcamento.getItens();

        double impostoTotal = 0;

        for(ItemPedidoModel item : itens){
            ProdutoModel produto = item.getProduto();
            if(produto.getDescricao().contains("*")){
                impostoTotal += produto.getPrecoUnitario() * item.getQuantidade() * 0.05;
            }
            else{
                impostoTotal += produto.getPrecoUnitario() * item.getQuantidade() * 0.15;
            }
        }

        return impostoTotal;
    }
    
}
