package com.bcopstein.sistvendas.interfaceAdaptadora;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.sistvendas.aplicacao.casosDeUso.ChegadaProdutoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.CriaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.EfetivaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.EstoquePorProdutosUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ProdutosDisponiveisUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.QuantidadeDisponivelUC;
import com.bcopstein.sistvendas.aplicacao.dtos.ItemEstoqueDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ProdutoDTO;
import com.bcopstein.sistvendas.auxiliares.Localidade;

@RestController
public class Controller {
    private ProdutosDisponiveisUC produtosDisponiveis;
    private CriaOrcamentoUC criaOrcamento;
    private EfetivaOrcamentoUC efetivaOrcamento;
    private ChegadaProdutoUC chegadaProduto;
    private QuantidadeDisponivelUC estoqueCompleto;
    private EstoquePorProdutosUC estoquePorProdutos;

    @Autowired
    public Controller(ProdutosDisponiveisUC produtosDisponiveis,
                      CriaOrcamentoUC criaOrcamento,
                      EfetivaOrcamentoUC efetivaOrcamento, 
                      ChegadaProdutoUC chegadaProduto,
                      QuantidadeDisponivelUC estoqueCompleto,
                      EstoquePorProdutosUC estoquePorProdutos) {
        this.produtosDisponiveis = produtosDisponiveis;
        this.criaOrcamento = criaOrcamento;
        this.efetivaOrcamento = efetivaOrcamento;
        this.chegadaProduto = chegadaProduto;
        this.estoqueCompleto = estoqueCompleto;
        this.estoquePorProdutos = estoquePorProdutos;
    }

    /*  Não tem data ainda
        · Retornar a lista de orçamentos efetivados em um determinado período (informar data inicial e data final) 
    */

    @GetMapping("")
    @CrossOrigin(origins = "*")
    public String welcomeMessage(){
        return("Bem vindo as lojas ACME");
    }

    // · Retornar o catálogo de produtos
    @GetMapping("produtosDisponiveis")
    @CrossOrigin(origins = "*")
    public List<ProdutoDTO> produtosDisponiveis(){
        return produtosDisponiveis.run();
    }    

    // · Solicitar orçamento para um pedido (lista de itens)
    @PostMapping("novoOrcamento")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO novoOrcamento(@RequestBody List<ItemPedidoDTO> itens, Localidade localidade){
        return criaOrcamento.run(itens, localidade);
    }

    // · Efetivar orçamento indicado se ainda for válido e houver produtos disponíveis
    @GetMapping("efetivaOrcamento/{id}")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO efetivaOrcamento(@PathVariable(value="id") long idOrcamento){
        return efetivaOrcamento.run(idOrcamento);
    }

    // · Informar a chegada de produtos no estoque
    @PostMapping("chegadaProduto")
    @CrossOrigin(origins = "*")
    public void chegadaProduto(@RequestBody List<ItemPedidoDTO> produtos){
        chegadaProduto.run(produtos);
    }

    // · Retornar a quantidade disponível no estoque para todos os itens do catálogo
    @GetMapping("estoqueCompleto")
    @CrossOrigin(origins = "*")
    public List<ItemEstoqueDTO> estoqueCompleto() {
        return estoqueCompleto.run();
    }

    // · Retornar a quantidade disponível no estoque para os itens do catálogo informados
    @PostMapping("estoquePorProdutos")
    @CrossOrigin(origins = "*")
    public List<ItemEstoqueDTO> estoquePorProdutos(@RequestBody List<Long> idsProdutos) {
        return estoquePorProdutos.run(idsProdutos);
    }

}