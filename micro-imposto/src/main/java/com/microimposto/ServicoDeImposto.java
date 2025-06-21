package com.microimposto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Service
public class ServicoDeImposto {
    
    @Value("${microservico.impostos.url:http://microservico-impostos:8081}")
    private String microservicoImpostosUrl;
    
    private final RestTemplate restTemplate;

    public ServicoDeImposto() {
        this.restTemplate = new RestTemplate();
    }

    // TODO: Revisar metodo para calcular o imposto

    public double calculaImposto(OrcamentoModel orcamento, String localidade) {
        try {
            String url = microservicoImpostosUrl + "/api/impostos/calcular";
            
            // Preparar o request
            Map<String, Object> request = new HashMap<>();
            request.put("orcamento", orcamento);
            request.put("localidade", localidade);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            
            // Fazer a chamada para o microserviço
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                Object valorImposto = responseBody.get("valorImposto");
                
                if (valorImposto instanceof Number) {
                    return ((Number) valorImposto).doubleValue();
                }
            }
            
            // Fallback: cálculo simples se o microserviço não estiver disponível
            return orcamento.getSubtotal() * 0.15; // 15% de imposto padrão
            
        } catch (Exception e) {
            System.err.println("Erro ao chamar microserviço de impostos: " + e.getMessage());
            // Fallback: cálculo simples
            return orcamento.getSubtotal() * 0.15;
        }
    }

    // TODO: Implementar método para obter detalhes do imposto
    public String getDetalhesImposto(String localidade) {
        // Implementar lógica para obter detalhes do imposto baseado na localidade
        // Por enquanto, retornando uma string fixa como exemplo
        return "Detalhes do imposto para a localidade: " + localidade;
    }
}

