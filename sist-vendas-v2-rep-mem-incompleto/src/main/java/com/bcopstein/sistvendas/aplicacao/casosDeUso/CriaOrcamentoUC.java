package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.auxiliares.Localidade;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.PedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeEstoque;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;

@Component
public class CriaOrcamentoUC {
    private ServicoDeVendas servicoDeVendas;
    private ServicoDeEstoque servicoDeEstoque;
    
    @Autowired
    public CriaOrcamentoUC(ServicoDeVendas servicoDeVendas,ServicoDeEstoque servicoDeEstoque){
        this.servicoDeVendas = servicoDeVendas;
        this.servicoDeEstoque = servicoDeEstoque;
    }

    public OrcamentoDTO run(List<ItemPedidoDTO> itens, Localidade localidade, Date data){ 
        PedidoModel pedido = new PedidoModel(localidade, data);
        for(ItemPedidoDTO item:itens){
            ProdutoModel produto = servicoDeEstoque.produtoPorCodigo(item.getProdutoId());
            ItemPedidoModel itemPedido = new ItemPedidoModel(produto, item.getQuantidade());
            pedido.addItem(itemPedido);
        }
        
        servicoDeVendas.criaPedido(pedido);
        OrcamentoModel orcamento = servicoDeVendas.criaOrcamento(pedido);
        // servicoDeVendas.relacionaItensOrcamento(orcamento, pedido);

        return OrcamentoDTO.fromModel(orcamento);
    }
}
