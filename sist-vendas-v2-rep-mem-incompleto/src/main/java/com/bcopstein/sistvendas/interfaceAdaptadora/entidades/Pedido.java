package com.bcopstein.sistvendas.interfaceAdaptadora.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.bcopstein.sistvendas.auxiliares.Localidade;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.PedidoModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id")
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

    public static PedidoModel toModel(Pedido pedido) {
        List<ItemPedidoModel> itensModel = new ArrayList<>();
        for (ItemPedido item : pedido.getItens()) {
            itensModel.add(item.toModel());
        }
        return new PedidoModel(pedido.id, pedido.local, pedido.data, itensModel);
    }

    public static Pedido fromModel(PedidoModel model) {
        Pedido pedido = new Pedido(model.getId(), model.getLocal(), model.getData());
        for (ItemPedidoModel itemModel : model.getItens()) {
            pedido.addItem(ItemPedido.fromModel(itemModel));
        }
        return pedido;
    }
}
