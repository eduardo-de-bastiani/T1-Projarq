package com.bcopstein.sistvendas.interfaceAdaptadora.entidades;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.bcopstein.sistvendas.auxiliares.Localidade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private List<ItemPedido> itens;
    private double custoItens;
    private double imposto;
    private double desconto;
    private double custoConsumidor; 
    private boolean efetivado;
    private Localidade localidade;
    private Date data;

    public Orcamento(long id) {
        this.id = id;
        this.itens = new LinkedList<>();
        this.efetivado = false;
    }

    protected Orcamento(){}

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
}
