package com.bcopstein.sistvendas.interfaceAdaptadora.implemRepositorios;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.sistvendas.auxiliares.DefaultException;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;
import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.Produto;
import com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA.ProdutoJPA_ItfRep;

@Repository
@Primary
public class ProdutoRepJPA implements IProdutoRepositorio {
    private ProdutoJPA_ItfRep produtoRepository;

    @Autowired
    public ProdutoRepJPA(ProdutoJPA_ItfRep produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoModel> todos() {
        List<Produto> produtos = produtoRepository.findAll();

        if (produtos.size() == 0) {
            throw new DefaultException("Nenhum produto.");
        }

        return produtos.stream()
                    .map(prod -> prod.toModel())
                    .toList();
    }

    public ProdutoModel consultaPorId(long id) {
        Optional<Produto> optProduto = produtoRepository.findById(id);

        if (optProduto.isEmpty()) {
            throw new DefaultException("Produto inexistente");
        }

        return optProduto.map(prod -> prod.toModel()).orElse(null);
    }
}
