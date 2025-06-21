package com.bcopstein.sistvendas.interfaceAdaptadora.entidades;

import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String descricao;
    private double precoUnitario;

    public Produto(long id, String descricao, double precoUnitario) {
        this.id = id;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
    }

    protected Produto(){}

    public long getId() {
        return this.id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoUnitario() {
        return this.precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String toString() {
        return "{" +
            " codigo=\'" + getId() + "\'" +
            ", descricao=\'" + getDescricao() + "\'" +
            ", precoUnitario=\'" + getPrecoUnitario() + "\'" +
            "}";
    }

    public static ProdutoModel toModel(Produto produto) {
        return new ProdutoModel(produto.getId(), produto.getDescricao(), produto.getPrecoUnitario());
    }

    public ProdutoModel toModel() {
        return new ProdutoModel(this.getId(), this.getDescricao(), this.getPrecoUnitario());
    }

    public static Produto fromModel(ProdutoModel model) {
        return new Produto(model.getId(), model.getDescricao(), model.getPrecoUnitario());
    }
}

