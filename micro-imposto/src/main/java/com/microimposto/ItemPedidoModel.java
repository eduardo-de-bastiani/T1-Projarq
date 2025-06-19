package com.microimposto;

public class ItemPedidoModel {
    private String codigoProduto;
    private int quantidade;
    private double precoUnitario;

    public ItemPedidoModel() {}

    public ItemPedidoModel(String codigoProduto, int quantidade, double precoUnitario) {
        this.codigoProduto = codigoProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    // Getters e Setters
    public String getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(String codigoProduto) { this.codigoProduto = codigoProduto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }
}

