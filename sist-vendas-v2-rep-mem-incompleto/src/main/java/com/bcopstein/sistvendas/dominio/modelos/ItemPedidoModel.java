package com.bcopstein.sistvendas.dominio.modelos;

public class ItemPedidoModel {
    private ProdutoModel produto;
    private int quantidade;
    private OrcamentoModel orcamento;
    private PedidoModel pedido;

    public ItemPedidoModel(ProdutoModel produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public OrcamentoModel getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(OrcamentoModel orcamento) {
        this.orcamento = orcamento;
    }

    public PedidoModel getPedido() {
        return pedido;
    }

    public void setPedido(PedidoModel pedido) {
        this.pedido = pedido;
    }

    public double getPrecoUnitario() {
        return produto.getPrecoUnitario();
    }

    public long getCodigoProduto() {
        return produto.getId();
    }

    @Override
    public String toString() {
        return "ItemPedidoModel [produto=" + produto + ", quantidade=" + quantidade + ", orcamento=" + orcamento
                + ", pedido=" + pedido + "]";
    }
}


