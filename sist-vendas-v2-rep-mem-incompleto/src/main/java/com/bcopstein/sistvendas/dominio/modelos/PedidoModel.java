package com.bcopstein.sistvendas.dominio.modelos;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.bcopstein.sistvendas.auxiliares.Localidade;

public class PedidoModel {
    private List<ItemPedidoModel> itens;
    private Localidade local;
    private Date data;

    public PedidoModel(Localidade local, Date data) {
        this.itens = new LinkedList<>();
        this.local = local;
        this.data = data;
    }

    public PedidoModel(long id, Localidade local, Date data, List<ItemPedidoModel> itens) {
        this.itens = itens;
        this.local = local;
        this.data = data;
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

    public List<ItemPedidoModel> getItens() {
        return new ArrayList<ItemPedidoModel>(itens);
    }

    public void addItem(ItemPedidoModel item){
        itens.add(item);
    }

    @Override
    public String toString() {
        return "PedidoModel [itens=" + itens + ", local=" + local + ", data=" + data + "]";
    }
}
