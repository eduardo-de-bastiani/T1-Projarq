package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.sistvendas.auxiliares.DefaultException;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.PedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IItemPedidoRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IPedidoRepositorio;

@Service
public class ServicoDeVendas {
    private IOrcamentoRepositorio orcamentos;
    private IPedidoRepositorio pedidos;
    private IItemPedidoRepositorio itemPedido;
    private IEstoqueRepositorio estoque;
    private ServicoDeImposto servicoDeImposto;

    @Autowired
    public ServicoDeVendas(IOrcamentoRepositorio orcamentos,IEstoqueRepositorio estoque, ServicoDeImposto servicoDeImposto, IPedidoRepositorio pedidos, IItemPedidoRepositorio itemPedido) {
        this.pedidos = pedidos;
        this.itemPedido = itemPedido;
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

        novoOrcamento.setCustoItens(custoItens);

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
        if (orcamento == null || orcamento.isEfetivado()) throw new DefaultException("Orçamento não encontrado ou já efetivado");
        
        // Verifica se tem quantidade em estoque para todos os itens
        List<ProdutoModel> produtosDisponiveis = produtosDisponiveis();

        for (ItemPedidoModel item : orcamento.getItens()) {
            ProdutoModel produto = item.getProduto();
            long produtoId = produto.getId();

            boolean produtoEncontrado = false;
            for (ProdutoModel produtoDisponivel : produtosDisponiveis) {
                if (produtoDisponivel.getId() == produtoId) {
                    produtoEncontrado = true;
                    break;
                }
            }

            if (!produtoEncontrado) {
                throw new DefaultException("Produto não encontrado no estoque: " + produto.getDescricao());
            }
            
            if (this.estoque.quantidadeEmEstoque(produto.getId()) < item.getQuantidade()) {
                throw new DefaultException("Quantidade insuficiente em estoque para o produto: " + produto.getDescricao()); 
            }
            
            if (item.getQuantidade() <= 0) {
                throw new DefaultException("Quantidade inválida para o produto: " + produto.getDescricao());
            }
        }

        // Se tem quantidade para todos os itens, da baixa no estoque para todos
        for (ItemPedidoModel item : orcamento.getItens()) {
            ProdutoModel produto = item.getProduto();
            this.estoque.baixaEstoque(produto.getId(), item.getQuantidade());
        }

        // Marca o orcamento como efetivado
        orcamento.efetiva();
        this.orcamentos.marcaComoEfetivado(id);

        // Retorna o orçamento marcado como efetivado ou não conforme disponibilidade do estoque
        return orcamento;
    }

    public List<OrcamentoModel> orcamentosDatas(String dataInicial, String dataFinal) {
        return this.orcamentos.recuperaData(dataInicial, dataFinal);
    }

    public void criaPedido(PedidoModel pedido) {
        this.pedidos.cadastraPedido(pedido);
    }

    public void relacionaItensOrcamento(OrcamentoModel orcamento, PedidoModel pedido) {
        for (ItemPedidoModel item : pedido.getItens()) {
            item.setOrcamento(orcamento);
            item.setPedido(pedido);
            this.itemPedido.cadastraItemPedido(item);
        }
    }
}
