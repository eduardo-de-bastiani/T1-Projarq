package com.bcopstein.sistvendas.interfaceAdaptadora.implemRepositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.sistvendas.auxiliares.DefaultException;
import com.bcopstein.sistvendas.dominio.modelos.ItemDeEstoqueModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.ItemDeEstoque;
import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.Produto;
import com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA.EstoqueJPA_ItfRep;


@Repository
@Primary
public class EstoqueRepJPA implements IEstoqueRepositorio{
    private EstoqueJPA_ItfRep estoque;

    @Autowired
    public EstoqueRepJPA(EstoqueJPA_ItfRep estoque){
        this.estoque = estoque;
    }

    @Override
    public List<ProdutoModel> todosComEstoque() {
        List<ItemDeEstoque> itens = estoque.findByQuantidadeGreaterThan(0);

        return itens.stream()
                .map(it -> it.getProduto().toModel())
                .toList();
    }

    @Override
    public List<ProdutoModel> todos() {
        List<ItemDeEstoque> itens = estoque.findAll();
        return itens.stream()
                .map(it -> it.getProduto().toModel())
                .toList();
    }

    @Override
    public int quantidadeEmEstoque(long codigo) {
        ItemDeEstoque item = estoque.findById(codigo).orElse(null);
        
        if (item == null){
            throw new DefaultException("Produto inexistente");
        }

        return item.getQuantidade();
    }

    @Override
    public void baixaEstoque(long codProd, int qtdade) {
        ItemDeEstoque item = estoque.findById(codProd).orElse(null);

        if (item == null){
            throw new DefaultException("Produto inexistente");
        }
        if (item.getQuantidade() < qtdade){
            throw new DefaultException("Quantidade em estoque insuficiente");
        }
        if (item.getEstoqueMin() > item.getQuantidade() - qtdade ) {
            throw new DefaultException("Quantidade em estoque ficará abaixo do mínimo");
        }

        int novaQuantidade = item.getQuantidade() - qtdade;
        item.setQuantidade(novaQuantidade);
        estoque.save(item);
    }

    @Override
    public void adicionaEstoque(long id, int qtdade) {
        ItemDeEstoque item = estoque.findById(id).orElse(null);

        if (item == null){
            throw new DefaultException("Produto inexistente");
        }
        if (item.getEstoqueMax() < item.getQuantidade() + qtdade) {
            throw new DefaultException("Quantidade em estoque excederá o máximo");
        }

        int novaQuantidade = item.getQuantidade() + qtdade;
        item.setQuantidade(novaQuantidade);
        estoque.save(item);
    }

    @Override
    public List<ItemDeEstoqueModel> estoqueCompleto() {
        List<ItemDeEstoque> itens = estoque.findAll();
        return itens.stream()
                .map(it -> new ItemDeEstoqueModel(it.getProduto().toModel(), it.getQuantidade(), it.getEstoqueMin(), it.getEstoqueMax()))
                .toList();
    }

    @Override
    public List<ItemDeEstoqueModel> estoquePorProdutos(List<Long> idsProdutos) {
        List<ItemDeEstoque> itens = estoque.findByProdutoIdIn(idsProdutos);

        if (itens.isEmpty()) {
            throw new DefaultException("Nenhum produto encontrado");
        }

        for (ItemDeEstoque item : itens) {
            if (item.getProduto() == null) {
                throw new DefaultException("Produto inexistente");
            }
        }
        
        return itens.stream()
                .map(it -> new ItemDeEstoqueModel(it.getProduto().toModel(), it.getQuantidade(), it.getEstoqueMin(), it.getEstoqueMax()))
                .toList();
    }

    @Override
    public Optional<ProdutoModel> findByCodigo(long codigo) {
        return estoque.findById(codigo)
                      .map(itemEstoque -> itemEstoque.getProduto().toModel());
    }

    @Override
    public ProdutoModel save(ProdutoModel produtoModel) {
        ItemDeEstoque itemEstoque = estoque.findById(produtoModel.getId())
                                          .orElseThrow(() -> new DefaultException("Produto não encontrado no estoque para salvar: " + produtoModel.getId()));
        
        Produto produtoEntity = itemEstoque.getProduto();
        produtoEntity.setDescricao(produtoModel.getDescricao());
        produtoEntity.setPrecoUnitario(produtoModel.getPrecoUnitario());
        
        estoque.save(itemEstoque);
        return produtoModel;
    }
}


