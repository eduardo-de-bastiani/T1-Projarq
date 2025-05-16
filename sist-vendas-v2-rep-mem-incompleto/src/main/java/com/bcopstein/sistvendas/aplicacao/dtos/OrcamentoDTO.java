package com.bcopstein.sistvendas.aplicacao.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bcopstein.sistvendas.auxiliares.Localidade;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;

public class OrcamentoDTO {
    private long id;
    private List<ItemPedidoDTO> itens;
    private double custoItens;
    private double imposto;
    private double desconto;
    private double custoConsumidor;
    private boolean efetivado;
    private Localidade localidade;
    private Date data;

    public OrcamentoDTO(long id,List<ItemPedidoDTO> itens,double custoItens,double imposto,double desconto, double custoConsumidor,boolean efetivado, Localidade localidade, Date data){
        this.id = id;
        this.itens = itens;
        this.custoItens = custoItens;
        this.imposto = imposto;
        this.desconto = desconto;
        this.custoConsumidor = custoConsumidor;
        this.efetivado = efetivado;
        this.localidade = localidade;
        this.data = data;
    }

    public OrcamentoDTO() { }

    public long getId() {
        return id;
    }

    public List<ItemPedidoDTO> getItens(){
        return itens;
    }

    public double getCustoItens() {
        return custoItens;
    }

    public double getImposto() {
        return imposto;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getCustoConsumidor() {
        return custoConsumidor;
    }

    public boolean isEfetivado() {
        return efetivado;
    }

    public void efetiva(){
        efetivado = true;
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public Date getData() {
        return data;
    }

    public static OrcamentoDTO fromModel(OrcamentoModel orcamento){
        List<ItemPedidoDTO> itens = new ArrayList<>(orcamento.getItens().size());
        for(ItemPedidoModel ip:orcamento.getItens()){
            itens.add(ItemPedidoDTO.fromModel(ip));
        }
        return new OrcamentoDTO(orcamento.getId(),itens,orcamento.getCustoItens(),
                                orcamento.getImposto(),orcamento.getDesconto(),orcamento.getCustoConsumidor(),orcamento.isEfetivado(), orcamento.getLocalidade(), orcamento.getData());
    }

    public static List<OrcamentoDTO> fromModel(List<OrcamentoModel> orcamentos){
        return orcamentos.stream()
                        .map(OrcamentoDTO::fromModel)
                        .collect(Collectors.toList());
    }
}
