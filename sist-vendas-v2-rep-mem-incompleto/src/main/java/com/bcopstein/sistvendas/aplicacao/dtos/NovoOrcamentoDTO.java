package com.bcopstein.sistvendas.aplicacao.dtos;

import java.util.Date;
import java.util.List;

import com.bcopstein.sistvendas.auxiliares.Localidade;

public class NovoOrcamentoDTO {
    private List<ItemPedidoDTO> itens;
    private Localidade localidade;
    private Date data;

    public NovoOrcamentoDTO(List<ItemPedidoDTO> itens, Localidade localidade, Date data) {
        this.itens = itens;
        this.localidade = localidade;
        this.data = data;
    }

    public NovoOrcamentoDTO() { }

    public Date getData() {
        return data;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public Localidade getLocalidade() {
        return localidade;
    }
}