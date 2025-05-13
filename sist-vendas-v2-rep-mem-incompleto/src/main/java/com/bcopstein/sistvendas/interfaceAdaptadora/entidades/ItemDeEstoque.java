package com.bcopstein.sistvendas.interfaceAdaptadora.entidades;
import com.bcopstein.sistvendas.dominio.modelos.ItemDeEstoqueModel;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemDeEstoque{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;
    private int quantidade;
    private int estoqueMin;
    private int estoqueMax;

    public ItemDeEstoque(long id, Produto produto, int quantidade, int estoqueMin, int estoqueMax) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.estoqueMin = estoqueMin;
        this.estoqueMax = estoqueMax;
    }

    protected ItemDeEstoque(){}

    public long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getEstoqueMin() {
        return estoqueMin;
    }

    public int getEstoqueMax() {
        return estoqueMax;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setEstoqueMin(int estoqueMin) {
        this.estoqueMin = estoqueMin;
    }

    public void setEstoqueMax(int estoqueMax) {
        this.estoqueMax = estoqueMax;
    }

    @Override
    public String toString() {
        return "ItemDeEstoque [id=" + id + ", produto=" + produto + ", quantidade=" + quantidade + ", estoqueMin="
                + estoqueMin + ", estoqueMax=" + estoqueMax + "]";
    }
   
    public ItemDeEstoqueModel toModel() {
        return new ItemDeEstoqueModel(this.produto.toModel(), this.quantidade, this.estoqueMin, this.estoqueMax);
    }

    public static ItemDeEstoque fromModel(ItemDeEstoqueModel model) {
        Produto produto = Produto.fromModel(model.getProduto());
        return new ItemDeEstoque(0, produto, model.getQuantidade(), model.getEstoqueMin(), model.getEstoqueMax());
    } 
}