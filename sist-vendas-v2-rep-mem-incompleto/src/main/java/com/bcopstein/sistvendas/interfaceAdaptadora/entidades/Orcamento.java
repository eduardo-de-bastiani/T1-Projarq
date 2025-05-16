package com.bcopstein.sistvendas.interfaceAdaptadora.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.bcopstein.sistvendas.auxiliares.Localidade;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orcamento_id")
    private List<ItemPedido> itens;
    private double custoItens;
    private double imposto;
    private double desconto;
    private double custoConsumidor; 
    private boolean efetivado;
    
    @Enumerated(EnumType.STRING)
    private Localidade localidade;
    private Date data;

    public Orcamento() {
        this.itens = new LinkedList<>();
        this.efetivado = false;
    }

    public void addItensPedido(Pedido pedido){
        for(ItemPedido itemPedido:pedido.getItens()){
            itens.add(itemPedido);
        }
    }

    public List<ItemPedido> getItens(){
        return itens;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public double getCustoItens() {
        return custoItens;
    }

    public void setCustoItens(double custoItens){
        this.custoItens = custoItens;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto){
        this.imposto = imposto;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto){
        this.desconto = desconto;
    }

    public double getCustoConsumidor() {
        return custoConsumidor;
    }

    public void setCustoConsumidor(double custoConsumidor){
        this.custoConsumidor = custoConsumidor;
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

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public static OrcamentoModel toModel(Orcamento orcamento) {
        OrcamentoModel model = new OrcamentoModel(orcamento.getId());
        List<ItemPedidoModel> itensModel = new ArrayList<>();
        for (ItemPedido item : orcamento.getItens()) {
            itensModel.add(item.toModel());
        }
        model.getItens().addAll(itensModel);
        model.setCustoItens(orcamento.getCustoItens());
        model.setImposto(orcamento.getImposto());
        model.setDesconto(orcamento.getDesconto());
        model.setCustoConsumidor(orcamento.getCustoConsumidor());
        if (orcamento.isEfetivado()) {
            model.efetiva();
        }
        model.setLocalidade(orcamento.getLocalidade());
        model.setData(orcamento.getData());
        return model;
    }

    public OrcamentoModel toModel() {
        OrcamentoModel model = new OrcamentoModel(this.getId());
        List<ItemPedidoModel> itensModel = new ArrayList<>();
        for (ItemPedido item : this.getItens()) {
            itensModel.add(item.toModel());
        }
        model.getItens().addAll(itensModel);
        model.setCustoItens(this.getCustoItens());
        model.setImposto(this.getImposto());
        model.setDesconto(this.getDesconto());
        model.setCustoConsumidor(this.getCustoConsumidor());
        if (this.isEfetivado()) {
            model.efetiva();
        }
        model.setLocalidade(this.getLocalidade());
        model.setData(this.getData());
        return model;
    }

    public static Orcamento fromModel(OrcamentoModel model) {
        Orcamento orcamento = new Orcamento();
        for (ItemPedidoModel itemModel : model.getItens()) {
            orcamento.getItens().add(ItemPedido.fromModel(itemModel));
        }
        orcamento.setCustoItens(model.getCustoItens());
        orcamento.setImposto(model.getImposto());
        orcamento.setDesconto(model.getDesconto());
        orcamento.setCustoConsumidor(model.getCustoConsumidor());
        if (model.isEfetivado()) {
            orcamento.efetiva();
        }
        orcamento.setLocalidade(model.getLocalidade());
        orcamento.setData(model.getData());
        return orcamento;
    }
}
