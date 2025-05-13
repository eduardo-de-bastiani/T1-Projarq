package com.bcopstein.sistvendas.interfaceAdaptadora.entidades;

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

    public double getPrecoUnitario() {
        return this.precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String toString() {
        return "{" +
            " codigo='" + getId() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", precoUnitario='" + getPrecoUnitario() + "'" +
            "}";
    }
}