package com.bcopstein.sistvendas.interfaceAdaptadora.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Produto produto;
    private int quantidade;
    
    public ItemPedido(long id, Produto produto, int quantidade) {
        this.id = id;
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
}
