package com.microimposto;

import java.util.List;

public class OrcamentoModel {
    private Long id;
    private double subtotal;
    private double imposto;
    private double total;
    private List<ItemPedidoModel> itens;

    public OrcamentoModel() {}

    public OrcamentoModel(Long id, double subtotal, double imposto, double total, List<ItemPedidoModel> itens) {
        this.id = id;
        this.subtotal = subtotal;
        this.imposto = imposto;
        this.total = total;
        this.itens = itens;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getImposto() { return imposto; }
    public void setImposto(double imposto) { this.imposto = imposto; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public List<ItemPedidoModel> getItens() { return itens; }
    public void setItens(List<ItemPedidoModel> itens) { this.itens = itens; }
}

