package com.bcopstein.sistvendas.aplicacao.dtos;

import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;

public class ItemEstoqueDTO {
    private long idProduto;
    private String nomeProduto;
    private int quantidade;

    public ItemEstoqueDTO(long idProduto, String nomeProduto, int quantidade) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
    }

    public ItemEstoqueDTO() {}

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }
    
    public static ItemEstoqueDTO fromModel(ProdutoModel produto, int quantidade) {
        return new ItemEstoqueDTO(produto.getId(), produto.getDescricao(), quantidade);
    }
}
