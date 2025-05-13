package com.bcopstein.sistvendas.interfaceAdaptadora.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.bcopstein.sistvendas.auxiliares.Localidade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private List<ItemPedido> itens;
    private Localidade local;
    private Date data;

    public Pedido(long id, Localidade local, Date data) {
        this.id = id;
        this.itens = new LinkedList<>();
        this.local = local;
        this.data = data;
    }

    protected Pedido(){}

    public long getId() {
        return id;
    }

    public Localidade getLocal() {
        return local;
    }

    public void setLocal(Localidade local) {
        this.local = local;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<ItemPedido>(itens);
    }

    public void addItem(ItemPedido item){
        itens.add(item);
    }
}
