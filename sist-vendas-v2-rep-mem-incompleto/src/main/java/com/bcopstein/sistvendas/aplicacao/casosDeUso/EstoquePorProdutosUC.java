package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.ItemEstoqueDTO;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeEstoque;

@Component
public class EstoquePorProdutosUC {
    private final ServicoDeEstoque servicoEstoque;

    @Autowired
    public EstoquePorProdutosUC(ServicoDeEstoque servicoEstoque) {
        this.servicoEstoque = servicoEstoque;
    }

    public List<ItemEstoqueDTO> run(List<Long> idsProdutos) {
        return servicoEstoque.estoquePorProdutos(idsProdutos);
    }
}

