package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.PedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;

@Service
public class ServicoDeVendas {
    private IOrcamentoRepositorio orcamentos;
    private IEstoqueRepositorio estoque;
    private ServicoDeImposto servicoDeImposto;

    @Autowired
    public ServicoDeVendas(IOrcamentoRepositorio orcamentos,IEstoqueRepositorio estoque, ServicoDeImposto servicoDeImposto){
        this.orcamentos = orcamentos;
        this.estoque = estoque;
        this.servicoDeImposto = servicoDeImposto;
    }
    
    public List<ProdutoModel> produtosDisponiveis() {
        return estoque.todosComEstoque();
    }

    public OrcamentoModel recuperaOrcamentoPorId(long id) {
        return this.orcamentos.recuperaPorId(id);
    }

    public OrcamentoModel criaOrcamento(PedidoModel pedido) {
        var novoOrcamento = new OrcamentoModel();
        novoOrcamento.setLocalidade(pedido.getLocal());
        novoOrcamento.setData(pedido.getData());
        novoOrcamento.addItensPedido(pedido);
        double custoItens = novoOrcamento.getItens().stream()
            .mapToDouble(it->it.getProduto().getPrecoUnitario()*it.getQuantidade())
            .sum();

        // Aqui devemos calcular o imposto de acordo com a localidade -> Devemos chamar o Strategy
        double valorImposto = servicoDeImposto.calculaImposto(novoOrcamento, pedido.getLocal());
        novoOrcamento.setImposto(valorImposto);

        if (novoOrcamento.getItens().size() > 5){
                novoOrcamento.setDesconto(custoItens * 0.05);
            }else{
                novoOrcamento.setDesconto(0.0);
            }
        novoOrcamento.setCustoConsumidor(custoItens + novoOrcamento.getImposto() - novoOrcamento.getDesconto());
        return this.orcamentos.cadastra(novoOrcamento);
    }
 
    public OrcamentoModel efetivaOrcamento(long id) {
        // Recupera o orçamento
        OrcamentoModel orcamento = this.orcamentos.recuperaPorId(id);
        if (orcamento == null || orcamento.isEfetivado()) return null;
        
        // Verifica se tem quantidade em estoque para todos os itens
        List<ProdutoModel> produtosDisponiveis = produtosDisponiveis();
        for (ItemPedidoModel item : orcamento.getItens()) {
            ProdutoModel produto = item.getProduto();
            if (!produtosDisponiveis.contains(produto) || this.estoque.quantidadeEmEstoque(produto.getId()) < item.getQuantidade()) {
                return null; 
            }
        }

        // Se tem quantidade para todos os itens, da baixa no estoque para todos
        for (ItemPedidoModel item : orcamento.getItens()) {
            ProdutoModel produto = item.getProduto();
            this.estoque.baixaEstoque(produto.getId(), item.getQuantidade());
        }

        // Marca o orcamento como efetivado
        orcamento.efetiva();

        // Retorna o orçamento marcado como efetivado ou não conforme disponibilidade do estoque
        return orcamento;
    }

    public List<OrcamentoModel> orcamentosDatas(String dataInicial, String dataFinal) {
        return this.orcamentos.recuperaData(dataInicial, dataFinal);
    }
}
