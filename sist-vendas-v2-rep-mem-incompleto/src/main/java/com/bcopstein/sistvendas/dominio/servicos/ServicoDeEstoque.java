package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;
import com.bcopstein.sistvendas.aplicacao.dtos.ItemEstoqueDTO;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;

@Service
public class ServicoDeEstoque{
    private IEstoqueRepositorio estoque;
    private IProdutoRepositorio produtos;
    
    @Autowired
    public ServicoDeEstoque(IProdutoRepositorio produtos,IEstoqueRepositorio estoque){
        this.produtos = produtos;
        this.estoque = estoque;
    }
 
    public List<ProdutoModel> produtosDisponiveis(){
        return estoque.todosComEstoque();
    }

    public ProdutoModel produtoPorCodigo(long id){
        return this.produtos.consultaPorId(id);
    }

    public int qtdadeEmEstoque(long id){
        return estoque.quantidadeEmEstoque(id);
    }

    public void baixaEstoque(long id,int qtdade){
        estoque.baixaEstoque(id,qtdade);
    }  

    public void chegadaDeProdutos(List<ItemPedidoModel> produtosModel) {
        for (ItemPedidoModel produto : produtosModel) {
            estoque.adicionaEstoque(produto.getProduto().getId(), produto.getQuantidade());
        }
    }

    public List<ItemEstoqueDTO> estoqueCompleto() {
        return estoque.estoqueCompleto();
    }

    public List<ItemEstoqueDTO> estoquePorProdutos(List<Long> idsProdutos) {
        return estoque.estoquePorProdutos(idsProdutos);
    }
}
