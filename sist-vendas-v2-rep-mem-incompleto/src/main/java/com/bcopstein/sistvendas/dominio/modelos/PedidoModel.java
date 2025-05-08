package com.bcopstein.sistvendas.dominio.modelos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.bcopstein.sistvendas.auxiliares.Localidade;

public class PedidoModel {
    private long id;
    private List<ItemPedidoModel> itens;
    private Localidade local;

    public void setLocal(Localidade local) {
        this.local = local;
    }

    public Localidade getLocal() {
        return local;
    }

    public PedidoModel(long id, Localidade local) {
        this.id = id;
        this.itens = new LinkedList<>();
        this.local = local;
    }

    public long getId() {
        return id;
    }

    public List<ItemPedidoModel> getItens() {
        return new ArrayList<ItemPedidoModel>(itens);
    }

    public void addItem(ItemPedidoModel item){
        itens.add(item);
    }
}
