package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;
import com.bcopstein.sistvendas.dominio.modelos.ItemDeEstoqueModel;
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
        return this.estoque.todosComEstoque();
    }

    public ProdutoModel produtoPorCodigo(long id){
        return this.produtos.consultaPorId(id);
    }

    public int qtdadeEmEstoque(long id){
        return this.estoque.quantidadeEmEstoque(id);
    }

    public void baixaEstoque(long id,int qtdade){
        this.estoque.baixaEstoque(id,qtdade);
    }  

    public void chegadaDeProdutos(List<ItemPedidoModel> produtosModel) {
        for (ItemPedidoModel produto : produtosModel) {
            this.estoque.adicionaEstoque(produto.getProduto().getId(), produto.getQuantidade());
        }
    }

    public List<ItemDeEstoqueModel> estoqueCompleto() {
        return this.estoque.estoqueCompleto();
    }

    public List<ItemDeEstoqueModel> estoquePorProdutos(List<Long> idsProdutos) {
        return this.estoque.estoquePorProdutos(idsProdutos);
    }
}
