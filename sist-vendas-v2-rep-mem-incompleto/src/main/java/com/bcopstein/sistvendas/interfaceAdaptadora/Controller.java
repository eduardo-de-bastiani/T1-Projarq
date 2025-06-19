package com.bcopstein.sistvendas.interfaceAdaptadora;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.sistvendas.aplicacao.casosDeUso.ChegadaProdutoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.CriaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.EfetivaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.EstoquePorProdutosUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.OrcamentosEfetivadosUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ProdutosDisponiveisUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.QuantidadeDisponivelUC;
import com.bcopstein.sistvendas.aplicacao.dtos.ItemEstoqueDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.NovoOrcamentoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ProdutoDTO;

@RestController
public class Controller {
    private ProdutosDisponiveisUC produtosDisponiveis;
    private CriaOrcamentoUC criaOrcamento;
    private EfetivaOrcamentoUC efetivaOrcamento;
    private ChegadaProdutoUC chegadaProduto;
    private QuantidadeDisponivelUC estoqueCompleto;
    private EstoquePorProdutosUC estoquePorProdutos;
    private OrcamentosEfetivadosUC orcamentosEfetivados;

    @Autowired
    public Controller(ProdutosDisponiveisUC produtosDisponiveis,
                      CriaOrcamentoUC criaOrcamento,
                      EfetivaOrcamentoUC efetivaOrcamento, 
                      ChegadaProdutoUC chegadaProduto,
                      QuantidadeDisponivelUC estoqueCompleto,
                      EstoquePorProdutosUC estoquePorProdutos,
                      OrcamentosEfetivadosUC orcamentosEfetivados) {
        this.produtosDisponiveis = produtosDisponiveis;
        this.criaOrcamento = criaOrcamento;
        this.efetivaOrcamento = efetivaOrcamento;
        this.chegadaProduto = chegadaProduto;
        this.estoqueCompleto = estoqueCompleto;
        this.estoquePorProdutos = estoquePorProdutos;
        this.orcamentosEfetivados = orcamentosEfetivados;
    }

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
    public OrcamentoDTO novoOrcamento(@RequestBody NovoOrcamentoDTO pedido){
        return criaOrcamento.run(pedido.getItens(), pedido.getLocalidade(), pedido.getData());
    }

    // · Efetivar orçamento indicado se ainda for válido e houver produtos disponíveis
    @GetMapping("efetivaOrcamento/{id}/{usuario}")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO efetivaOrcamento(@PathVariable(value="id") long idOrcamento, @PathVariable(value="usuario") String usuario){
        return efetivaOrcamento.run(idOrcamento, usuario);
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

    // · Retornar a lista de orçamentos efetivados em um determinado período (informar data inicial e data final) 
    @PostMapping("orcamentosEfetivados")
    @CrossOrigin(origins = "*")
    public List<OrcamentoDTO> orcamentosEfetivados(@RequestBody List<String> datas) {
        return orcamentosEfetivados.run(datas.get(0), datas.get(1));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleErroGeral(Exception ex) {
        return Map.of("erro", ex.getMessage());
    }
}