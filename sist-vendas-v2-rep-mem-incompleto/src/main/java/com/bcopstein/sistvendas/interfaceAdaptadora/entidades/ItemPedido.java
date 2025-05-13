package com.bcopstein.sistvendas.interfaceAdaptadora.entidades;

import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;
    private int quantidade;
    
    public ItemPedido(long id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    protected ItemPedido(){}

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItemPedido [produto=" + produto + ", quantidade=" + quantidade + "]";
    }

    public static ItemPedidoModel toModel(ItemPedido itemPedido) {
        return new ItemPedidoModel(itemPedido.getProduto().toModel(), itemPedido.getQuantidade());
    }

    public ItemPedidoModel toModel() {
        return new ItemPedidoModel(this.getProduto().toModel(), this.getQuantidade());
    }

    public static ItemPedido fromModel(ItemPedidoModel model) {
        Produto produto = Produto.fromModel(model.getProduto());
        return new ItemPedido(produto, model.getQuantidade());
    }
}
