package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.bcopstein.sistvendas.auxiliares.DefaultException;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;

@Service
public class ServicoDeVendas {
    
    @Value("${microservico.registro.url:http://microservico-registro:8082}")
    private String microservicoRegistroUrl;
    
    private IOrcamentoRepositorio orcamentos;
    private IEstoqueRepositorio estoque;
    private ServicoDeImposto servicoDeImposto;
    private final RestTemplate restTemplate;

    @Autowired
    public ServicoDeVendas(IOrcamentoRepositorio orcamentos,
                                   IEstoqueRepositorio estoque,
                                   ServicoDeImposto servicoDeImposto) {
        this.orcamentos = orcamentos;
        this.estoque = estoque;
        this.servicoDeImposto = servicoDeImposto;
        this.restTemplate = new RestTemplate();
    }

    public OrcamentoModel criaOrcamento(List<ItemPedidoModel> itens, String localidade) {
        // Calcula o subtotal
        double subtotal = itens.stream().mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade()).sum();

        // Calcula o imposto usando o microserviço
        OrcamentoModel orcamentoTemp = new OrcamentoModel(0L, subtotal, 0.0, 0.0, itens);
        double imposto = servicoDeImposto.calculaImposto(orcamentoTemp, localidade);

        // Calcula o total
        double total = subtotal + imposto;

        // Cria o orçamento
        OrcamentoModel orcamento = new OrcamentoModel(0L, subtotal, imposto, total, itens);

        // Persiste o orçamento
        orcamentos.save(orcamento);

        return orcamento;
    }

    public OrcamentoModel efetivaOrcamento(Long idOrcamento, String localidade) {
        OrcamentoModel orcamento = orcamentos.findById(idOrcamento).orElse(null);

        if (orcamento == null) {
            throw new DefaultException("Orcamento nao encontrado: " + idOrcamento);
        }

        // Verifica se há estoque para todos os produtos do orçamento
        for (ItemPedidoModel item : orcamento.getItens()) {
            ProdutoModel produto = estoque.findByCodigo(item.getCodigoProduto()).orElse(null);
            if (produto == null || produto.getQuantidade() < item.getQuantidade()) {
                throw new DefaultException("Estoque insuficiente para o produto: " + item.getCodigoProduto());
            }
        }

        // Baixa o estoque dos produtos
        for (ItemPedidoModel item : orcamento.getItens()) {
            ProdutoModel produto = estoque.findByCodigo(item.getCodigoProduto()).get();
            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            estoque.save(produto);
        }

        // Registra o imposto no microserviço de registro
        registrarImpostoNoMicroservico(orcamento, localidade);

        return orcamento;
    }

    private void registrarImpostoNoMicroservico(OrcamentoModel orcamento, String localidade) {
        try {
            String url = microservicoRegistroUrl + "/api/registro/impostos";
            
            // Preparar o request
            Map<String, Object> request = new HashMap<>();
            request.put("orcamentoId", orcamento.getId());
            request.put("valorVendido", orcamento.getSubtotal());
            request.put("valorImposto", orcamento.getImposto());
            request.put("localidade", localidade);
            request.put("dataEfetivacao", LocalDateTime.now().toString());
            request.put("detalhesImposto", "Imposto calculado para localidade: " + localidade);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            
            // Fazer a chamada para o microserviço
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                System.err.println("Erro ao registrar imposto no microserviço: " + response.getStatusCode());
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao chamar microserviço de registro: " + e.getMessage());
            // Não falha a operação principal se o registro falhar
        }
    }
}

